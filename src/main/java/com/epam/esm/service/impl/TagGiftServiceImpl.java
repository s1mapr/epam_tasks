package com.epam.esm.service.impl;

import com.epam.esm.dao.TagGiftRepository;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagGiftServiceImpl implements TagGiftService {
    private final TagGiftRepository tagGiftRepository;
    private final TagService tagServiceImpl;
    private final GiftCertificateService giftCertificateServiceImpl;
    private final OrderService orderService;

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
        tagGiftRepository.save(new TagGift(giftCertificate, tag));
    }

    private void deleteEntryByTagId(int tagId){
        tagGiftRepository.deleteTagGiftByTagId(tagId);
    }

    private void deleteEntryByCertificateId(int certificateId){
        tagGiftRepository.deleteTagGiftByGiftCertificateId(certificateId);
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
        orderService.deleteOrderByGiftCertificateId(certificateId);
        giftCertificateServiceImpl.deleteGiftCertificateById(certificateId);
    }
    public List<TagDTO> getTagsByCertificateId(int id) {
        GiftCertificate certificate = giftCertificateServiceImpl.getGiftCertificateById(id);
        List<TagGift> tagGifts = tagGiftRepository.getTagGiftByGiftCertificateId(certificate.getId());
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

        Map<Tag, Integer> tagCountMap = orders.stream()
                .map(Order::getGiftCertificate)
                .flatMap(giftCertificate -> giftCertificate.getGiftCertificateTags().stream())
                .map(TagGift::getTag)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.summingInt(tag -> 1)
                ));

        Map.Entry<Tag, Integer> mostUsedTagEntry = tagCountMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if(Objects.isNull(mostUsedTagEntry)){
            throw new BadRequestException("There are no user orders");
        }

        Tag mostUsedTag = mostUsedTagEntry.getKey();
        return TagDTO.createDTO(mostUsedTag);
    }

}
