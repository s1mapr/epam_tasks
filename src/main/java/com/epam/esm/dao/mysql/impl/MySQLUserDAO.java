package com.epam.esm.dao.mysql.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.*;
import com.epam.esm.exeptions.BadRequestException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class MySQLUserDAO implements UserDAO {

    private static final int LIMIT_FOR_PAGINATION = 10;

    public EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> getUserById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return Optional.ofNullable(currentSession.get(User.class, id));
    }

    @Override
    public List<User> getAllUsersWithPagination(Integer page) {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<User> query = currentSession.createQuery("from User", User.class);
        if (Objects.nonNull(page)) {
            if (page <= 0 || page > Math.ceil((double) query.getResultList().size() /10)) {
                throw new BadRequestException("page " + page + " are not available");
            }
            query.setFirstResult(LIMIT_FOR_PAGINATION * (page-1));
            query.setMaxResults(LIMIT_FOR_PAGINATION);
        }
        return query.getResultList();
    }

    @Override
    public int createUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(user);
        return user.getId();
    }


    @Override
    public Optional<User> getUserWithHighestCostOfAllOrders() {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Order> orders = currentSession.createQuery(" FROM Order", Order.class).getResultList();
        Map<User, Double> usersWithCostOfOrders = new HashMap<>();

        for (Order order : orders) {
            User user = order.getUser();
            usersWithCostOfOrders.put(user, usersWithCostOfOrders.getOrDefault(user, 0.0) + order.getCost());
        }

        User userWithHighestCostOfOrders = null;
        double maxCount = 0;

        for (Map.Entry<User, Double> entry : usersWithCostOfOrders.entrySet()) {
            User user = entry.getKey();
            double count = entry.getValue();

            if (count > maxCount) {
                maxCount = count;
                userWithHighestCostOfOrders = user;
            }
        }
        return Optional.ofNullable(userWithHighestCostOfOrders);
    }
}
