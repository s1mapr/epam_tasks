package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO getUserDTOById(int id);
    List<UserDTO> getAllUsersWithPagination(Integer page);
    int createUser(User user);
    User getUserById(int id);
    default User getUserWithHighestCostOfAllOrders(){
        return new User();
    }
}
