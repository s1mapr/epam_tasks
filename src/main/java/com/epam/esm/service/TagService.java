package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getAllTags();
    void createTag(String name);
    void deleteTagById(int id);
    Optional<Tag> getTagByName(String name);
    List<Tag> getTagsByCertificateId(int id);
    Tag getTagById(int id);
}
