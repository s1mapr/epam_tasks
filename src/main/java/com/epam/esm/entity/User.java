package com.epam.esm.entity;

import com.epam.esm.util.auditors.UserAuditor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Builder
@EntityListeners(UserAuditor.class)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String userName;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
