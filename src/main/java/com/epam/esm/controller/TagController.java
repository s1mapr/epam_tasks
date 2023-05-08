package com.epam.esm.controller;

import com.epam.esm.dto.MessageDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagServiceImpl tagServiceImpl;

    private final TagGiftServiceImpl tagGiftService;

    /**
     * Method for creating tag
     * tagRequestDTO contains name, description, price, duration, create date and last update date
     *
     * @param tag certificate to create
     */
    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody Tag tag) {
        tagServiceImpl.createTag(tag.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * Method for getting all tags that exist in DB
     * If there are no any tags, empty list will be returned
     *
     * @return all tags in JSON format
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return new ResponseEntity<>(tagServiceImpl.getAllTags(), HttpStatus.OK);
    }

    /**
     * Method for deleting tag
     *
     * @param id is identifier of tag to delete
     * @return message that contains data about request
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteTagById(@PathVariable Integer id) {
        tagGiftService.deleteTagById(id);
        return ResponseEntity.ok(MessageDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Deleted tag with id " + id)
                .build());
    }

    /**
     * Method for getting tag by id
     * If there are no any tag with such id, error message will be returned
     *
     * @return tag in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") int id) {
        return new ResponseEntity<>(tagServiceImpl.getTagById(id), HttpStatus.OK);
    }

}
