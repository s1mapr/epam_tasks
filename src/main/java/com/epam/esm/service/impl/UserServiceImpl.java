package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserDTO getUserDTOById(int id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new BadRequestException("User with id " + id + " not found"));
        return UserDTO.createDTO(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id).orElseThrow(() -> new BadRequestException("User with id " + id + " not found"));
    }


    @Override
    public List<UserDTO> getAllUsersWithPagination(Integer page) {
        List<User> list = userRepository.findAll(PageRequest.of(page, 10)).getContent();
        return list.stream()
                .map(UserDTO::createDTO)
                .collect(Collectors.toList());
    }


    @Override
    public int createUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName).orElseThrow(()->new BadRequestException("no user with such username"));
    }


}
