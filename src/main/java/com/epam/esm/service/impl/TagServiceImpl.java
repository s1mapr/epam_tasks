package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public List<TagDTO> getAllTagsWithPagination(Integer page) {
        return tagDAO.getAllTagsWithPagination(page).stream()
                .map(TagDTO::createDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createTag(String name) {
        Tag tag = Tag.builder()
                .name(name)
                .build();
        tagDAO.createTag(tag);
    }

    @Transactional
    public void deleteTagById(int id) {
        tagDAO.deleteTagById(id);
    }

    public Optional<Tag> getOptionalTagByName(String name) {

        return tagDAO.getTagByName(name);
    }


    public TagDTO getTagByName(String name) {
        Tag tag = tagDAO.getTagByName(name).orElseThrow(()->new BadRequestException("No tag with same name"));
        return TagDTO.createDTO(tag);
    }





    @Override
    public Tag getTagById(int id) {
        return tagDAO.getTagById(id).orElseThrow(() -> new BadRequestException("Tag with id " + id + " not found"));
    }


}
