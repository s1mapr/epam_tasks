package serviceTest;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testSaveOrder() {
        Order order = Order.builder()
                .id(1)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        int savedId = orderService.saveOrder(order);

        assertEquals(1, savedId);
    }


    @Test
    public void testGetOrderById() {
        Order order = new Order();
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1);

        assertNotNull(result);
    }

    @Test
    public void testGetAllUserOrders() {
        User user = User.builder()
                .id(1)
                .build();

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(orderRepository.getOrdersByUserId(1)).thenReturn(orders);

        List<Order> result = orderService.getAllUserOrders(user);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteOrderByGiftCertificateId() {
        assertDoesNotThrow(() -> orderService.deleteOrderByGiftCertificateId(1));
        verify(orderRepository).deleteOrderByGiftCertificateId(1);
    }
}