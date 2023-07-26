package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
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

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order.getUserId(), order.getCertificateId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = OrderDTO.createDTO(order);
        orderDTO.add(linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getId())).withSelfRel());
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

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
