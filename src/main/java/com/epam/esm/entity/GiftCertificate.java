package com.epam.esm.entity;

import com.epam.esm.util.ISO8601TimeFormatter;
import com.epam.esm.util.auditors.GiftCertificateAuditor;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EntityListeners(GiftCertificateAuditor.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private int duration;
    @Column
    private String createDate;
    @Column
    private String lastUpdateDate;
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
    @OneToMany(mappedBy = "giftCertificate")
    private List<TagGift> giftCertificateTags = new ArrayList<>();
    @OneToMany(mappedBy = "giftCertificate")
    private List<Order> orders = new ArrayList<>();

    public static GiftCertificate builderForUpdatingData(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate){
        return GiftCertificate.builder()
                .id(oldGiftCertificate.getId())
                .name(newGiftCertificate.getName() == null ? oldGiftCertificate.getName() : newGiftCertificate.getName())
                .description(newGiftCertificate.getDescription() == null ? oldGiftCertificate.getDescription() : newGiftCertificate.getDescription())
                .price(newGiftCertificate.getPrice() == 0 ? oldGiftCertificate.getPrice() : newGiftCertificate.getPrice())
                .duration(newGiftCertificate.getDuration() == 0 ? oldGiftCertificate.getDuration() : newGiftCertificate.getDuration())
                .createDate(oldGiftCertificate.getCreateDate())
                .lastUpdateDate(ISO8601TimeFormatter.getFormattedDate(new Date()))
                .image(newGiftCertificate.getImage() == null ? oldGiftCertificate.getImage() : newGiftCertificate.getImage())
                .build();
    }
}
