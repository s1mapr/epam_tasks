package controllerTest;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.Role;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserOrderService;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserOrderService userOrderService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        User user = User.builder()
                .userName("name")
                .role(Role.USER)
                .password("123")
                .age(21)
                .build();
        Order order = Order.builder()
                .id(1)
                .giftCertificate(new GiftCertificate())
                .user(user)
                .cost(50.2)
                .timeStamp(LocalDate.now().toString())
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrderById() throws Exception {
        int orderId = 1;
        Order order = Order.builder()
                .id(orderId)
                .user(User.builder().id(1).build())
                .giftCertificate(GiftCertificate.builder().id(1).build())
                .timeStamp(LocalDate.now().toString())
                .cost(111.1)
                .build();

        when(orderService.getOrderById(1)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));
    }

    @Test
    public void testGetUserOrders() throws Exception{
        User user = User.builder().id(1).build();
        List<OrderDTO> orders = Arrays.asList(
                OrderDTO.builder().id(1).userId(1).certificateId(1).timeStamp(LocalDate.now().toString()).cost(12.1).build(),
                OrderDTO.builder().id(2).userId(1).certificateId(2).timeStamp(LocalDate.now().toString()).cost(13.2).build(),
                OrderDTO.builder().id(3).userId(1).certificateId(3).timeStamp(LocalDate.now().toString()).cost(2.11).build());

        when(userService.getUserById(user.getId())).thenReturn(user);
        when(orderService.getAllUserOrdersDTO(user, null)).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/userOrders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
