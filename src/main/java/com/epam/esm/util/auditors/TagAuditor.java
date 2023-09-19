package com.epam.esm.util.auditors;

import com.epam.esm.entity.Tag;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

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
