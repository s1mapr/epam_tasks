package com.epam.esm.dao;

import com.epam.esm.dto.TagGiftId;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.TagGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagGiftRepository extends JpaRepository<TagGift, TagGiftId> {
    void deleteTagGiftByGiftCertificateId(int giftCertificateId);
    void deleteTagGiftByTagId(int tagId);
    List<TagGift> getTagGiftsByTagId(int tagId);
    List<TagGift> getTagGiftByGiftCertificateId(int giftCertificateId);
}
