package serviceTest;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringConfig.class)
@ActiveProfiles("test")
@Transactional
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void reset(){
        try (Connection connection = dataSource.getConnection()){
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("create.sql"));
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("insert.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateOrder(){
        int certificateId = 4;
        int userId = 1;
        Integer orderId = orderService.createOrder(userId, certificateId);
        assertNotNull(orderId);
        Order order = orderService.getOrderById(orderId);
        assertEquals(56.5, order.getCost());
    }

    @Test
    public void testGetAllUserOrdersDTO(){
        int userId = 1;
        User user = userService.getUserById(userId);
        List<OrderDTO> orderList = orderService.getAllUserOrdersDTO(user, 1);
        assertEquals(2, orderList.size());
    }

    @Test
    public void testGetAllUserOrders(){
        int userId = 1;
        User user = userService.getUserById(userId);
        List<Order> orders = orderService.getAllUserOrders(user);
        assertEquals(2, orders.size());
    }
}