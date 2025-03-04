package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.MessageDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.dto.SearchParams;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final TagGiftServiceImpl tagGiftServiceImpl;
    private final TagServiceImpl tagService;

    /**
     * Method for getting all gift certificates
     * If there are no any gift certificates, empty list will be returned
     *
     * @return list of all gift certificates in JSON format
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificates(@RequestParam(value = "p", required = false) Integer page) {
        List<GiftCertificateDTO> certificates = tagGiftServiceImpl.getAllGiftCertificateDTOWithPagination(page);
        for (GiftCertificateDTO certificate : certificates) {
            certificate.add(linkTo(methodOn(GiftCertificateController.class).getCertificateById(certificate.getId())).withRel("certificate"));
            certificate.getTags().stream().map(x -> x.add(linkTo(methodOn(TagController.class).getTagById(x.getId())).withRel("tag"))).collect(Collectors.toList());
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * Method for creating gift certificate
     * If there are no any gift certificates, empty list will be returned
     *
     * @param giftCertificate contains name, description, price, duration, create date and last updaet date
     * @return message about request status
     */
    @PostMapping
    public ResponseEntity<MessageDTO> createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificate) {
        int id = tagGiftServiceImpl.createGiftCertificate(giftCertificate);
        return ResponseEntity.ok(MessageDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Created gift certificate with id " + id)
                .build());
    }

    /**
     * Method for deleting gift certificate
     *
     * @param id is identifier of certificate to delete
     * @return message about request status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Integer id) {
        tagGiftServiceImpl.deleteCertificateById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Method for updating gift certificate
     * Updates gift certificate by its identifier
     *
     * @param id              is identifier of certificate to update
     * @param giftCertificate contains name, description, price, duration, create date, last update date, list of tags (All fields are optional)
     * @return message about request status
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageDTO> updateGiftCertificateById(@PathVariable Integer id,
                                                                @RequestBody GiftCertificateDTO giftCertificate) {
        tagGiftServiceImpl.updateGiftCertificate(giftCertificate, id);
        return ResponseEntity.ok(MessageDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Updated gift certificate with id " + id)
                .build());
    }

    /**
     * Method for getting list of gift certificates sorted and filtered by request params
     * If there are no any gift certificates with such parameters, empty list will be returned
     *
     * @param tagNames     list of tag names of certificates to search
     * @param name         part of certificate name to search
     * @param description  part of certificate description to search
     * @param isSortByDate shows if user want to sort certificates by date
     * @param isSortByName shows if user want to sort certificates by name
     * @param sortType     shows type of sorting (can be ASC or DESC)
     * @return list of gift certificates in JSON format
     */
    @GetMapping("/search")
    public ResponseEntity<List<GiftCertificateDTO>> getCertificatesByFilterParams(@RequestParam(value = "tagName", required = false) List<String> tagNames,
                                                                                  @RequestParam(value = "name", required = false) String name,
                                                                                  @RequestParam(value = "description", required = false) String description,
                                                                                  @RequestParam(value = "isSortByDate", required = false) boolean isSortByDate,
                                                                                  @RequestParam(value = "isSortByName", required = false) boolean isSortByName,
                                                                                  @RequestParam(value = "sortType", required = false) String sortType,
                                                                                  @RequestParam(value = "p", required = false) Integer page) {
        List<TagDTO> tags = new ArrayList<>();
        if (Objects.nonNull(tagNames)) {
            tags = tagNames.stream().map(tagService::getTagByName).collect(Collectors.toList());
        }
        List<GiftCertificateDTO> certificates = tagGiftServiceImpl.getCertificatesBySearchParams(SearchParams.builder()
                .tagName(tags)
                .name(name)
                .description(description)
                .isSortByDate(isSortByDate)
                .isSortByName(isSortByName)
                .sortType(sortType)
                .page(page)
                .build());
        for (GiftCertificateDTO certificate : certificates) {
            certificate.add(linkTo(methodOn(GiftCertificateController.class).getCertificateById(certificate.getId())).withRel("certificate"));
            if (Objects.nonNull(certificate.getTags())) {
                certificate.getTags().stream().map(x -> x.add(linkTo(methodOn(TagController.class).getTagById(x.getId())).withRel("tag"))).collect(Collectors.toList());
            }
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * Method for getting gift certificate by id
     * If there are no any certificate with such id, error message will be returned
     *
     * @return gift certificate in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getCertificateById(@PathVariable("id") int id) {
        GiftCertificateDTO giftCertificateDTO = tagGiftServiceImpl.getCertificateDTOById(id);
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).getAllGiftCertificates(1)).withRel("certificates"));
        giftCertificateDTO.add(linkTo(methodOn(GiftCertificateController.class).getCertificateById(giftCertificateDTO.getId())).withSelfRel());
        if (Objects.nonNull(giftCertificateDTO.getTags())) {
            giftCertificateDTO.getTags().stream().map(x -> x.add(linkTo(methodOn(TagController.class).getTagById(x.getId())).withRel("tag"))).collect(Collectors.toList());
        }
        return new ResponseEntity<>(giftCertificateDTO, HttpStatus.OK);
    }

}
