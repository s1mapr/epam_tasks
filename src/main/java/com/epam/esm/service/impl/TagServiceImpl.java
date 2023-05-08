package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public List<Tag> getAllTags(){
        return tagDAO.getAllTags();
    }

    @Transactional
    public void createTag(String name){
        tagDAO.createTag(name);
    }

    @Transactional
    public void deleteTagById(int id){
        tagDAO.deleteTagById(id);
    }

    public Optional<Tag> getTagByName(String name){
        return tagDAO.getTagByName(name);
    }

    public List<Tag> getTagsByCertificateId(int id){
        return tagDAO.getTagsByCertificateId(id);
    }

    @Override
    public Tag getTagById(int id) {
        return tagDAO.getTagById(id).orElseThrow(()->new BadRequestException("Tag with id " + id + " not found"));
    }

}
