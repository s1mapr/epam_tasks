package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserOrderService {
    int createOrder(Integer userId, Integer certificateId);
    User getUserWithHighestCostOfAllOrders();
    void createOrders (Integer userId, List<GiftCertificate> certificates);
}
