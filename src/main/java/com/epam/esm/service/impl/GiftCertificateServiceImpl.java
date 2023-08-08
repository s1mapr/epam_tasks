package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;

    @Transactional
    public int createGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateRepository.save(giftCertificate).getId();
    }

    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateRepository.findAll();
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesWithPagination(Integer page) {
        return giftCertificateRepository.findAll(PageRequest.of(page, 10)).getContent();
    }


    public GiftCertificate getGiftCertificateById(int id) {
        return giftCertificateRepository.getGiftCertificateById(id).orElseThrow(() -> new BadRequestException("Gift certificate with id " + id + " not found"));
    }

    @Transactional
    public void deleteGiftCertificateById(int id) {
        giftCertificateRepository.deleteGiftCertificateById(id);
    }

    @Transactional
    public void updateGiftCertificate(GiftCertificate newGiftCertificate, int id) {
        GiftCertificate oldGiftCertificate = getGiftCertificateById(id);
        GiftCertificate giftCertificate = GiftCertificate.builderForUpdatingData(newGiftCertificate, oldGiftCertificate);
        giftCertificateRepository.save(giftCertificate);

    }
}
