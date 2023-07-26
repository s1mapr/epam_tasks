package com.epam.esm.dao.postgresql.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.util.ISO8601TimeFormatter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PostgreSQLOrderDAO implements OrderDAO {

    private EntityManager entityManager;

    private static final int LIMIT_FOR_PAGINATION = 10;


    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int createOrder(User user, GiftCertificate giftCertificate) {
        Session currentSession = entityManager.unwrap(Session.class);
        String date = ISO8601TimeFormatter.getFormattedDate(new Date());
        Order order = Order.builder()
                .user(user)
                .giftCertificate(giftCertificate)
                .timeStamp(date)
                .cost(giftCertificate.getPrice())
                .build();
        currentSession.save(order);
        return order.getId();
    }

    @Override
    public Optional<Order> getOrderById(int orderId) {
        Session currentSession = entityManager.unwrap(Session.class);
        return Optional.ofNullable(currentSession.get(Order.class, orderId));
    }

    @Override
    public List<Order> getAllUserOrders(int userId, Integer page) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Order> query = currentSession.createQuery("FROM Order o WHERE o.user.id = :userId", Order.class);
        query.setParameter("userId", userId);
        if (Objects.nonNull(page)) {
            if (page <= 0 || page > Math.ceil((double) query.getResultList().size() /10)) {
                throw new BadRequestException("page " + page + " are not available");
            }
            query.setFirstResult(LIMIT_FOR_PAGINATION * (page-1));
            query.setMaxResults(LIMIT_FOR_PAGINATION);
        }
        return query.getResultList();
    }
}
