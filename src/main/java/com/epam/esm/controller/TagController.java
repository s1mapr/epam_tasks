package com.epam.esm.controller;

import com.epam.esm.dto.MessageDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagServiceImpl tagServiceImpl;

    private final TagGiftServiceImpl tagGiftService;

    private final UserService userService;

    private final OrderService orderService;
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
    public ResponseEntity<List<TagDTO>> getAllTags(@RequestParam(value = "p", required = false) Integer page) {
        List<TagDTO> tags = tagServiceImpl.getAllTagsWithPagination(page);
            for (TagDTO tag : tags) {
                tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("tag"));
            }
        return new ResponseEntity<>(tags, HttpStatus.OK);
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
    public ResponseEntity<TagDTO> getTagById(@PathVariable("id") int id) {
        TagDTO tag = tagGiftService.getTagDTOById(id);
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).getAllTags(1)).withRel("tags"));
        tag.add(linkTo(methodOn(TagController.class).getMostWildlyUsedTagOfUserWithHighestCostOfAllOrders()).withRel("mostWildlyUsedTag"));
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/mostWildlyUsedTag")
    public ResponseEntity<TagDTO> getMostWildlyUsedTagOfUserWithHighestCostOfAllOrders(){
        User user = userService.getUserWithHighestCostOfAllOrders();
        List<Order> orders = orderService.getAllUserOrders(user);
        TagDTO mostWildlyUsedTag = tagGiftService.getMostUsedTag(orders);
        mostWildlyUsedTag.add(linkTo(methodOn(TagController.class).getMostWildlyUsedTagOfUserWithHighestCostOfAllOrders()).withSelfRel());
        mostWildlyUsedTag.add(linkTo(methodOn(TagController.class).getAllTags(1)).withRel("tags"));
        mostWildlyUsedTag.add(linkTo(methodOn(TagController.class).getTagById(mostWildlyUsedTag.getId())).withRel("tag"));
        return new ResponseEntity<>(mostWildlyUsedTag, HttpStatus.OK);
    }

}
