package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    private final UserService userService;

    private final GiftCertificateService giftCertificateService;


    @Override
    @Transactional
    public int createOrder(Integer userId, Integer certificateId) {
        User user = UserDTO.extractUserFromDTO(userService.getUserDTOById(userId));
        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificateById(certificateId);
        return orderDAO.createOrder(user, giftCertificate);
    }


    @Override
    public List<OrderDTO> getAllUserOrdersDTO(User user, Integer page) {
        return orderDAO.getAllUserOrders(user.getId(), page)
                .stream()
                .map(OrderDTO::createDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId).orElseThrow(()->new BadRequestException("Order with id " + orderId + " not found"));
    }


    @Override
    public List<Order> getAllUserOrders(User user) {
        return user.getOrders();
    }

}
