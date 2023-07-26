package com.epam.esm.service.impl;

import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagGiftServiceImpl implements TagGiftService {
    private final TagGiftDAO tagGiftDAO;
    private final TagService tagServiceImpl;
    private final GiftCertificateService giftCertificateServiceImpl;

    @Transactional
    public int createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = GiftCertificateDTO.extractGiftCertificateFromDTO(giftCertificateDTO);
        if (Objects.nonNull(giftCertificateDTO.getTags()) && !giftCertificateDTO.getTags().isEmpty()) {
            giftCertificateDTO.getTags().stream()
                    .map(TagDTO::getName)
                    .filter(name -> tagServiceImpl.getOptionalTagByName(name).isEmpty())
                    .forEach(tagServiceImpl::createTag);

        }
        int id = giftCertificateServiceImpl.createGiftCertificate(giftCertificate);
        if (Objects.nonNull(giftCertificateDTO.getTags()) && !giftCertificateDTO.getTags().isEmpty()) {
            giftCertificateDTO.getTags().stream()
                    .map(TagDTO::getName)
                    .map(tagName -> tagServiceImpl.getOptionalTagByName(tagName).get().getId())
                    .forEach(tagId -> addEntry(tagId, id));
        }
        return id;
    }

    @Transactional
    public void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO, int id) {
        GiftCertificate giftCertificate = GiftCertificateDTO.extractGiftCertificateFromDTO(giftCertificateDTO);
        if (Objects.nonNull(giftCertificateDTO.getTags()) && !giftCertificateDTO.getTags().isEmpty()) {
            giftCertificateDTO.getTags().stream()
                    .map(TagDTO::getName)
                    .filter(name -> tagServiceImpl.getOptionalTagByName(name).isEmpty())
                    .forEach(tagServiceImpl::createTag);
            giftCertificateDTO.getTags().stream()
                    .map(TagDTO::getName)
                    .map(tagName -> tagServiceImpl.getOptionalTagByName(tagName).get().getId())
                    .forEach(tagId -> addEntry(tagId, id));
        }
        giftCertificateServiceImpl.updateGiftCertificate(giftCertificate, id);
    }


    private void addEntry(int tagId, int certificateId) {
        Tag tag = tagServiceImpl.getTagById(tagId);
        GiftCertificate giftCertificate = giftCertificateServiceImpl.getGiftCertificateById(certificateId);
        tagGiftDAO.addEntry(tag, giftCertificate);
    }

    private void deleteEntryByTagId(int tagId){
        tagGiftDAO.deleteEntryByTagId(tagId);
    }

    private void deleteEntryByCertificateId(int certificateId){
        tagGiftDAO.deleteEntryByCertificateId(certificateId);
    }

    public List<GiftCertificateDTO> getAllGiftCertificateDTO() {
        List<GiftCertificate> listOfCertificates = giftCertificateServiceImpl.getAllGiftCertificates();
        return listOfCertificates.stream()
                .map(GiftCertificateDTO::createDTO)
                .peek(dto -> dto.setTags(getTagsByCertificateId(dto.getId())))
                .collect(Collectors.toList());
    }
    public List<GiftCertificateDTO> getAllGiftCertificateDTOWithPagination(Integer page) {
        List<GiftCertificate> listOfCertificates = giftCertificateServiceImpl.getAllGiftCertificatesWithPagination(page);
        return listOfCertificates.stream()
                .map(GiftCertificateDTO::createDTO)
                .peek(dto -> dto.setTags(getTagsByCertificateId(dto.getId())))
                .collect(Collectors.toList());
    }

    public GiftCertificateDTO getCertificateDTOById(int id){
        GiftCertificate giftCertificate = giftCertificateServiceImpl.getGiftCertificateById(id);
        List<TagDTO> tags = getTagsByCertificateId(id);
        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.createDTO(giftCertificate);
        giftCertificateDTO.setTags(tags);
        return giftCertificateDTO;
    }

    @Transactional
    public List<GiftCertificateDTO> getCertificatesBySearchParams(SearchParams searchParams) {
        List<GiftCertificateDTO> certificateDTOS = getAllGiftCertificateDTO();
        SearchFilter searchFilter = new SearchFilter(certificateDTOS, searchParams);
        return searchFilter.doFilter();
    }

    @Transactional
    public void deleteTagById(int tagId){
        deleteEntryByTagId(tagId);
        tagServiceImpl.deleteTagById(tagId);
    }

    @Transactional
    public void deleteCertificateById(int certificateId){
        deleteEntryByCertificateId(certificateId);
        giftCertificateServiceImpl.deleteGiftCertificateById(certificateId);
    }
    public List<TagDTO> getTagsByCertificateId(int id) {
        GiftCertificate certificate = giftCertificateServiceImpl.getGiftCertificateById(id);
        List<TagGift> tagGifts = certificate.getGiftCertificateTags();
        return tagGifts.stream()
                .map(TagGift::getTag)
                .map(TagDTO::createDTO)
                .collect(Collectors.toList());
    }
    @Override
    public TagDTO getTagDTOById(int id) {
        Tag tag = tagServiceImpl.getTagById(id);
        return TagDTO.createDTO(tag);
    }

    public TagDTO getMostUsedTag(List<Order> orders) {
        Map<Tag, Integer> tagCountMap = new HashMap<>();

        for (Order order : orders) {
            GiftCertificate giftCertificate = order.getGiftCertificate();
            List<TagGift> giftCertificateTags = giftCertificate.getGiftCertificateTags();

            for (TagGift tagGift : giftCertificateTags) {
                Tag tag = tagGift.getTag();
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            }
        }

        Tag mostUsedTag = null;
        int maxCount = 0;

        for (Map.Entry<Tag, Integer> entry : tagCountMap.entrySet()) {
            Tag tag = entry.getKey();
            int count = entry.getValue();

            if (count > maxCount) {
                maxCount = count;
                mostUsedTag = tag;
            }
        }

        return TagDTO.createDTO(mostUsedTag);
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByTagId(int id) {
        return tagGiftDAO.getGiftCertificatesByTagId(id);
    }

    @Override
    public List<TagGift> getAllEntries() {
        return tagGiftDAO.getAllEntries();
    }
}
