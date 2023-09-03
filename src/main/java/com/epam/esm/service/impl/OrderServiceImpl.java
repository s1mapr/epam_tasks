package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public int saveOrder(Order order) {
        return orderRepository.save(order).getId();
    }

    @Override
    public List<OrderDTO> getAllUserOrdersDTO(User user, Integer page) {
        return orderRepository.getOrdersByUserId(user.getId(), PageRequest.of(page, 10))
                .stream()
                .map(OrderDTO::createDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.getOrderById(orderId).orElseThrow(()->new BadRequestException("Order with id " + orderId + " not found"));
    }


    @Override
    public List<Order> getAllUserOrders(User user) {
        return orderRepository.getOrdersByUserId(user.getId());
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrderByGiftCertificateId(int giftCertificateId) {
        orderRepository.deleteOrderByGiftCertificateId(giftCertificateId);
    }
}
