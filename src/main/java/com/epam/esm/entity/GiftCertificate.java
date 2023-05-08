package com.epam.esm.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificate {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;

    public static GiftCertificate builderForUpdatingData(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate){
        return GiftCertificate.builder()
                .id(oldGiftCertificate.getId())
                .name(newGiftCertificate.getName() == null ? oldGiftCertificate.getName() : newGiftCertificate.getName())
                .description(newGiftCertificate.getDescription() == null ? oldGiftCertificate.getDescription() : newGiftCertificate.getDescription())
                .price(newGiftCertificate.getPrice() == 0 ? oldGiftCertificate.getPrice() : newGiftCertificate.getPrice())
                .duration(newGiftCertificate.getDuration() == 0 ? oldGiftCertificate.getDuration() : newGiftCertificate.getDuration())
                .lastUpdateDate(newGiftCertificate.getLastUpdateDate() == null ? oldGiftCertificate.getLastUpdateDate() : newGiftCertificate.getLastUpdateDate())
                .build();
    }
}
