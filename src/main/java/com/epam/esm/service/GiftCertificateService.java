package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    int createGiftCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getAllGiftCertificates();
    List<GiftCertificate> getAllGiftCertificatesWithPagination(Integer page);
    GiftCertificate getGiftCertificateById(int id);
    void deleteGiftCertificateById(int id);
    void updateGiftCertificate(GiftCertificate newGiftCertificate, int id);
}
