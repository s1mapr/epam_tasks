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
    private int age;
    private Role role;

    public static UserDTO createDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .password(user.getPassword())
                .age(user.getAge())
                .role(user.getRole())
                .build();
    }

    public static User extractUserFromDTO(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .userName(userDTO.userName)
                .password(userDTO.getPassword())
                .age(userDTO.getAge())
                .role(userDTO.getRole())
                .build();
    }
}
