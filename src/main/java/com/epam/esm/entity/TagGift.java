package com.epam.esm.entity;

import com.epam.esm.dto.TagGiftId;
import com.epam.esm.util.auditors.TagGiftCertificateAuditor;
import com.epam.esm.util.auditors.UserAuditor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
