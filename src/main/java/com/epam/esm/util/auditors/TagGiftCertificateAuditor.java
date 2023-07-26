package com.epam.esm.util.auditors;

import com.epam.esm.entity.TagGift;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class TagGiftCertificateAuditor {
    @PrePersist
    private void prePersist(TagGift tagGift){
        System.out.println("TagGift entry with tagId " + tagGift.getTag().getId() + " and with giftCertificateId "+ tagGift.getGiftCertificate().getId() +" was created");
    }

    @PreUpdate
    private void preUpdate(TagGift tagGift){
        System.out.println("TagGift with tagId " + tagGift.getTag().getId() + " and with giftCertificateId "+ tagGift.getGiftCertificate().getId() +" was updated");

    }

    @PreRemove
    private void preRemoved(TagGift tagGift) {
        System.out.println("TagGift with tagId " + tagGift.getTag().getId() + " and with giftCertificateId "+ tagGift.getGiftCertificate().getId() + " was removed");

    }

}
