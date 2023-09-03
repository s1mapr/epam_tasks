package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<TagDTO> getAllTagsWithPagination(Integer page);
    int createTag(String name);
    void deleteTagById(int id);
    Optional<Tag> getOptionalTagByName(String name);
    Tag getTagById(int id);
    TagDTO getTagByName(String name);
}
