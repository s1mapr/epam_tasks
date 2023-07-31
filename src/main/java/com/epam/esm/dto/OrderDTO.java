package com.epam.esm.dto;


import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends RepresentationModel<OrderDTO> {
    private Integer id;
    private Integer userId;
    private Integer certificateId;
    private String timeStamp;
    private Double cost;

    public static OrderDTO createDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .certificateId(order.getGiftCertificate().getId())
                .timeStamp(order.getTimeStamp())
                .cost(order.getCost())
                .build();
    }

}
