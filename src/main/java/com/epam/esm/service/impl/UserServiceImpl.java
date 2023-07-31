package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;


    @Override
    public UserDTO getUserDTOById(int id) {
        User user = userDAO.getUserById(id).orElseThrow(() -> new BadRequestException("User with id " + id + " not found"));
        return UserDTO.createDTO(user);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id).orElseThrow(() -> new BadRequestException("User with id " + id + " not found"));
    }


    @Override
    public List<UserDTO> getAllUsersWithPagination(Integer page) {
        List<User> list = userDAO.getAllUsersWithPagination(page);
        return list.stream()
                .map(UserDTO::createDTO)
                .collect(Collectors.toList());
    }


    @Override
    public int createUser(User user) {
        return userDAO.createUser(user);
    }


    @Override
    public User getUserWithHighestCostOfAllOrders() {
        return userDAO.getUserWithHighestCostOfAllOrders().orElseThrow(()->new BadRequestException("There are no any user"));
    }
}
