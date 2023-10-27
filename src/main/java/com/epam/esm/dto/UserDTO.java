package com.epam.esm.dto;

import com.epam.esm.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
public class UserDTO extends RepresentationModel<UserDTO> {
    private Integer id;
    private String userName;
    private String password;
    private Role role;
    private String email;
    private String address;
    private String firstName;

    public static UserDTO createDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .address(user.getAddress())
                .firstName(user.getFirstName())
                .build();
    }

    public static User extractUserFromDTO(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .firstName(userDTO.getFirstName())
                .build();
    }
}
