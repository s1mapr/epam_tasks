package com.epam.esm.util.auditors;

import com.epam.esm.entity.User;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class UserAuditor {

    @PrePersist
    private void prePersist(User user){
        System.out.println("User with name " + user.getUsername() + " was created");
    }

    @PreUpdate
    private void preUpdate(User user){
        System.out.println("User with id " + user.getId() + " was updated");

    }

    @PreRemove
    private void preRemoved(User user) {
        System.out.println("User with id " + user.getId() + " was removed");

    }

}
