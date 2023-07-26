package com.epam.esm.util.auditors;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.aspectj.weaver.ast.Or;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

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
