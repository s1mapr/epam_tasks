package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserOrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ISO8601TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.cert.Certificate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {
    private final OrderService orderService;

    private final UserService userService;

    private final GiftCertificateService giftCertificateService;


    @Override
    @Transactional
    public int createOrder(Integer userId, Integer certificateId) {
        User user = UserDTO.extractUserFromDTO(userService.getUserDTOById(userId));
        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificateById(certificateId);
        Order order = Order.builder()
                .user(user)
                .giftCertificate(giftCertificate)
                .cost(giftCertificate.getPrice())
                .timeStamp(ISO8601TimeFormatter.getFormattedDate(new Date()))
                .build();
        return orderService.saveOrder(order);
    }

    @Override
    public User getUserWithHighestCostOfAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if(orders.isEmpty()){
            throw new BadRequestException("There are no orders in app");
        }
        Map<User, Double> usersWithCostOfOrders = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getUser,
                        Collectors.summingDouble(Order::getCost)
                ));
        User userWithHighestCostOfOrders = usersWithCostOfOrders.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return  Optional.ofNullable(userWithHighestCostOfOrders).orElseThrow(()->new BadRequestException("There are no users in app"));
    }

    @Override
    @Transactional
    public void createOrders(Integer userId, List<GiftCertificate> certificates) {
        new Thread(
            ()->{
                for(GiftCertificate certificate : certificates){
                    createOrder(userId, certificate.getId());
                }
            }
        ).start();
    }

}
