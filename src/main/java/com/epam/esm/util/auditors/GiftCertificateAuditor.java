package com.epam.esm.util.auditors;

import com.epam.esm.entity.GiftCertificate;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

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
