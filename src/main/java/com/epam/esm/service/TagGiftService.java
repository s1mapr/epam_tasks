package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.SearchParams;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.TagGift;

import java.util.List;

public interface TagGiftService {
    int createGiftCertificate(GiftCertificateDTO giftCertificateDTO);

    List<GiftCertificateDTO> getCertificatesBySearchParams(SearchParams searchParams);

    void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO, int id);

    void deleteTagById(int tagId);

    void deleteCertificateById(int certificateId);

    TagDTO getTagDTOById(int id);

    List<TagDTO> getTagsByCertificateId(int id);

    GiftCertificateDTO getCertificateDTOById(int id);
    List<GiftCertificateDTO> getAllGiftCertificateDTOWithPagination(Integer page);
    TagDTO getMostUsedTag(List<Order> orders);

}