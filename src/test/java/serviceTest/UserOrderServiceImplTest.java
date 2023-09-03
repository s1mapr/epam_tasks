package serviceTest;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserOrderServiceImplTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private GiftCertificateService giftCertificateService;

    @InjectMocks
    private UserOrderServiceImpl userOrderService;

    @Test
    public void testCreateOrder() {
        UserDTO userDTO = UserDTO.builder().id(1).build();

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setPrice(100.0);

        when(userService.getUserDTOById(1)).thenReturn(userDTO);
        when(giftCertificateService.getGiftCertificateById(1)).thenReturn(giftCertificate);
        when(orderService.saveOrder(any())).thenReturn(1);

        int result = userOrderService.createOrder(1, 1);

        assertEquals(1, result);
    }

    @Test
    public void testGetUserWithHighestCostOfAllOrders() {
        User user = User.builder().id(1).build();

        Order order = Order.builder().user(user).cost(100.0).build();

        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        User result = userOrderService.getUserWithHighestCostOfAllOrders();

        assertEquals(user.getId(), result.getId());
    }

}
