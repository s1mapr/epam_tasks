package com.epam.esm.service;

import com.epam.esm.entity.User;

public interface UserOrderService {
    int createOrder(Integer userId, Integer certificateId);
    User getUserWithHighestCostOfAllOrders();
}
