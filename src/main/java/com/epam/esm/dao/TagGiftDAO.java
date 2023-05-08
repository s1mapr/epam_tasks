package com.epam.esm.dao;

public interface TagGiftDAO {
    void addEntry(int tagId, int certificateId);
    void deleteEntryByTagId(int tagId);
    void deleteEntryByCertificateId(int certificateId);

}
