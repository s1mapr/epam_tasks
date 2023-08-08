package com.epam.esm.entity;

import com.epam.esm.dto.TagGiftId;
import com.epam.esm.util.auditors.TagGiftCertificateAuditor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "certificate_tag")
@EntityListeners(TagGiftCertificateAuditor.class)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TagGiftId.class)
public class TagGift {
    @Id
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private GiftCertificate giftCertificate;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
