package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;

    @Transactional
    public int createGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDAO.createGiftCertificate(giftCertificate);
    }

    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateDAO.getAllGiftCertificates();
    }



    public GiftCertificate getGiftCertificateById(int id) {
        return giftCertificateDAO.getGiftCertificateById(id).orElseThrow(() -> new BadRequestException("Gift certificate with id " + id + " not found"));
    }

    @Transactional
    public void deleteGiftCertificateById(int id) {
        giftCertificateDAO.deleteGiftCertificate(id);
    }

    @Transactional
    public void updateGiftCertificate(GiftCertificate newGiftCertificate, int id) {
        GiftCertificate oldGiftCertificate = getGiftCertificateById(id);
        GiftCertificate giftCertificate = GiftCertificate.builderForUpdatingData(newGiftCertificate, oldGiftCertificate);
        giftCertificateDAO.updateGiftCertificate(giftCertificate);

    }
}
