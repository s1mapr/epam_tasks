package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;

import java.util.List;

public interface TagGiftDAO {
    void addEntry(Tag tag, GiftCertificate giftCertificate);
    void deleteEntryByTagId(int tagId);
    void deleteEntryByCertificateId(int giftCertificateId);
    List<GiftCertificate> getGiftCertificatesByTagId(int id);

    default List<TagGift> getAllEntries(){
        return null;
    }
}
