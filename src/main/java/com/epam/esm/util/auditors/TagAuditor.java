package com.epam.esm.util.auditors;

import com.epam.esm.entity.Tag;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class TagAuditor {
    @PrePersist
    private void prePersist(Tag tag){
        System.out.println("Tag with name " + tag.getName() + " was created");
    }

    @PreUpdate
    private void preUpdate(Tag tag){
        System.out.println("Tag with id " + tag.getId() + " was updated");

    }

    @PreRemove
    private void preRemoved(Tag tag) {
        System.out.println("Tag with id " + tag.getId() + " was removed");

    }

}
