package com.epam.esm.entity;

import com.epam.esm.util.auditors.OrderAuditor;
import com.epam.esm.util.auditors.UserAuditor;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(OrderAuditor.class)
@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String timeStamp;

    @Column
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "gift_id")
    private GiftCertificate giftCertificate;

}
