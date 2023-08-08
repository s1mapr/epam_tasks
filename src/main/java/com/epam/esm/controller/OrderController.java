package com.epam.esm.controller;

import com.epam.esm.dto.MessageDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserOrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final UserOrderService userOrderService;
    private final OrderService orderService;
    private final UserService userService;

    /**
     * Method for creating order
     * OrderDTO contains userId, certificateId, timestamp and price
     *
     * @param order order to create
     */
    @PostMapping
    public ResponseEntity<MessageDTO> createOrder(@RequestBody OrderDTO order) {
        int id = userOrderService.createOrder(order.getUserId(), order.getCertificateId());
        return ResponseEntity.ok(MessageDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Created order with id " + id)
                .build());
    }

    /**
     * Method for getting order by id
     *
     * @param id is identifier of order
     *
     * @return order in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = OrderDTO.createDTO(order);
        orderDTO.add(linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getId())).withSelfRel());
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    /**
     * Method for getting user orders by user id
     *
     * @param id is identifier of user
     *
     * @return list of orders in JSON format
     */
    @GetMapping("/userOrders/{id}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable("id") int id,
                                                        @RequestParam(value = "p", required = false) Integer page){
        User user = userService.getUserById(id);
        List<OrderDTO> orders = orderService.getAllUserOrdersDTO(user,page);
        for(OrderDTO order : orders){
            order.add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withRel("order"));
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
