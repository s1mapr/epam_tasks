package com.epam.esm.service.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTagsWithPagination(Integer page) {
        return tagRepository.findAll(PageRequest.of(page, 10)).stream()
                .map(TagDTO::createDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public int createTag(String name) {
        Tag tag = Tag.builder()
                .name(name)
                .build();
        return tagRepository.save(tag).getId();
    }

    @Transactional
    public void deleteTagById(int id) {
        tagRepository.deleteTagById(id);
    }

    public Optional<Tag> getOptionalTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    public TagDTO getTagByName(String name) {
        Tag tag = tagRepository.getTagByName(name).orElseThrow(()->new BadRequestException("No tag with same name"));
        return TagDTO.createDTO(tag);
    }

    @Override
    public Tag getTagById(int id) {
        return tagRepository.getTagById(id).orElseThrow(() -> new BadRequestException("Tag with id " + id + " not found"));
    }

}
