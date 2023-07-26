package com.epam.esm.util.auditors;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class GiftCertificateAuditor {
    @PrePersist
    private void prePersist(GiftCertificate giftCertificate){
        System.out.println("Gift certificate with name " + giftCertificate.getName() + " was created");
    }

    @PreUpdate
    private void preUpdate(GiftCertificate giftCertificate){
        System.out.println("Gift certificate with id " + giftCertificate.getId() + " was updated");

    }

    @PreRemove
    private void preRemoved(GiftCertificate giftCertificate) {
        System.out.println("Gift certificate with id " + giftCertificate.getId() + " was removed");

    }

}
