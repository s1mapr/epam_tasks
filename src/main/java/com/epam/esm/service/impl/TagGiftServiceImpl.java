package com.epam.esm.service.impl;

import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                    .map(Tag::getName)
                    .filter(name -> tagServiceImpl.getTagByName(name).isEmpty())
                    .forEach(tagServiceImpl::createTag);

        }
        int id = giftCertificateServiceImpl.createGiftCertificate(giftCertificate);
        if (Objects.nonNull(giftCertificateDTO.getTags()) && !giftCertificateDTO.getTags().isEmpty()) {
            giftCertificateDTO.getTags().stream()
                    .map(Tag::getName)
                    .map(tagName -> tagServiceImpl.getTagByName(tagName).get().getId())
                    .forEach(tagId -> addEntry(tagId, id));
        }
        return id;
    }

    @Transactional
    public void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO, int id) {
        GiftCertificate giftCertificate = GiftCertificateDTO.extractGiftCertificateFromDTO(giftCertificateDTO);
        if (Objects.nonNull(giftCertificateDTO.getTags()) && !giftCertificateDTO.getTags().isEmpty()) {
            giftCertificateDTO.getTags().stream()
                    .map(Tag::getName)
                    .filter(name -> tagServiceImpl.getTagByName(name).isEmpty())
                    .forEach(tagServiceImpl::createTag);
            giftCertificateDTO.getTags().stream()
                    .map(Tag::getName)
                    .map(tagName -> tagServiceImpl.getTagByName(tagName).get().getId())
                    .forEach(tagId -> addEntry(tagId, id));
        }
        giftCertificateServiceImpl.updateGiftCertificate(giftCertificate, id);
    }


    private void addEntry(int tagId, int certificateId) {
        tagGiftDAO.addEntry(tagId, certificateId);
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
                .peek(dto -> dto.setTags(tagServiceImpl.getTagsByCertificateId(dto.getId())))
                .collect(Collectors.toList());
    }

    public GiftCertificateDTO getCertificateDTOById(int id){
        GiftCertificate giftCertificate = giftCertificateServiceImpl.getGiftCertificateById(id);
        List<Tag> tags = tagServiceImpl.getTagsByCertificateId(id);
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
}
