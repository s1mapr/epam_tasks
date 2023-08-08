package com.epam.esm.util.auditors;

import com.epam.esm.entity.Order;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class OrderAuditor {
    @PrePersist
    private void prePersist(Order order){
        System.out.println("Order was created");
    }

    @PreUpdate
    private void preUpdate(Order order){
        System.out.println("Order with id " + order.getId() + " was updated");

    }

    @PreRemove
    private void preRemoved(Order order) {
        System.out.println("Order with id " + order.getId() + " was removed");

    }

}
