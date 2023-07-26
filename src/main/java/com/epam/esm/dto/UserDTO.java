package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
public class UserDTO extends RepresentationModel<UserDTO> {
    private Integer id;
    private String userName;

    public static UserDTO createDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .build();
    }

    public static User extractUserFromDTO(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .userName(userDTO.userName)
                .build();
    }
}
