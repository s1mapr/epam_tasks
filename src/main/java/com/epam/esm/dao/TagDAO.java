package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {
    List<Tag> getAllTags();

    Optional<Tag> getTagByName(String name);

    void createTag(String name);

    void deleteTagById(int id);

    List<Tag> getTagsByCertificateId(int id);

    Optional<Tag> getTagById(int id);
}
