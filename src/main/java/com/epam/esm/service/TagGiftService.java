package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.SearchParams;

import java.util.List;

public interface TagGiftService {
    int createGiftCertificate(GiftCertificateDTO giftCertificateDTO);
    List<GiftCertificateDTO> getCertificatesBySearchParams(SearchParams searchParams);
    void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO, int id);
    void deleteTagById(int tagId);
    void deleteCertificateById(int certificateId);
}
