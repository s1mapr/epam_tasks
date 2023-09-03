package com.epam.esm.entity;


import com.epam.esm.util.auditors.TagAuditor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EntityListeners(TagAuditor.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Integer id;
    @Column
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<TagGift> giftCertificateTags = new ArrayList<>();

}
