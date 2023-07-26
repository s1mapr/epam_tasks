package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    int createOrder(User user, GiftCertificate giftCertificate);
    Optional<Order> getOrderById(int orderId);

    List<Order> getAllUserOrders(int userId, Integer page);
}
