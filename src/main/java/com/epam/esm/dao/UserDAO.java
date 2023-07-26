package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> getUserById(int id);
    List<User> getAllUsersWithPagination(Integer page);
    int createUser(User user);
    default Optional<User> getUserWithHighestCostOfAllOrders(){
        return Optional.empty();
    }

}
