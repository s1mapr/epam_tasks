package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer>, PagingAndSortingRepository<GiftCertificate, Integer> {
    Optional<GiftCertificate> getGiftCertificateById(int id);
    void deleteGiftCertificateById(int id);
}
