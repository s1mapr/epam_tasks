package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {

    int createGiftCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> getAllGiftCertificates();
    List<GiftCertificate> getAllGiftCertificatesWithPagination(Integer page);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(int id);

    Optional<GiftCertificate> getGiftCertificateById(int id);



}
