package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface OrderService {
    int saveOrder(Order order);
    List<OrderDTO> getAllUserOrdersDTO(User user, Integer page);
    List<Order> getAllUserOrders(User user);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    void deleteOrderByGiftCertificateId(int giftCertificateId);
}
