package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {
    List<Tag> getAllTagsWithPagination(Integer page);

    Optional<Tag> getTagByName(String name);

    int createTag(Tag tag);

    void deleteTagById(int id);

    Optional<Tag> getTagById(int id);

}
