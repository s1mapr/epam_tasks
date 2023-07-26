package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<TagDTO> getAllTagsWithPagination(Integer page);
    void createTag(String name);
    void deleteTagById(int id);
    Optional<Tag> getOptionalTagByName(String name);
    Tag getTagById(int id);
    default Tag getMostWildlyUsedTag(List<Order> orders){
        return new Tag();
    }
}
