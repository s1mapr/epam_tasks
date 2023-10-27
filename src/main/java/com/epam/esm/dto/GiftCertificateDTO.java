package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
    private byte[] image;
    private List<TagDTO> tags;

    public static GiftCertificate extractGiftCertificateFromDTO(GiftCertificateDTO giftCertificateDTO){
        return GiftCertificate.builder()
                .name(giftCertificateDTO.getName())
                .description(giftCertificateDTO.getDescription())
                .price(giftCertificateDTO.getPrice())
                .duration(giftCertificateDTO.getDuration())
                .image(giftCertificateDTO.getImage())
                .build();
    }

    public static GiftCertificateDTO createDTO(GiftCertificate giftCertificate){
        return GiftCertificateDTO.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .image(giftCertificate.getImage())
                .build();
    }
}
