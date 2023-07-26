package com.epam.esm.dao.postgresql.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PostgreSQLUserDAO implements UserDAO {
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
        User user = currentSession.createQuery("SELECT o.user FROM Order o WHERE o.cost = (SELECT MAX(o2.cost) FROM Order o2)", User.class).getSingleResult();
        return Optional.ofNullable(user);
    }
}
