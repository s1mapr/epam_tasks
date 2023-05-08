package com.epam.esm.dto;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tags;

    public static GiftCertificate extractGiftCertificateFromDTO(GiftCertificateDTO giftCertificateDTO){
        return GiftCertificate.builder()
                .name(giftCertificateDTO.getName())
                .description(giftCertificateDTO.getDescription())
                .price(giftCertificateDTO.getPrice())
                .duration(giftCertificateDTO.getDuration())
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
                .build();
    }
}
