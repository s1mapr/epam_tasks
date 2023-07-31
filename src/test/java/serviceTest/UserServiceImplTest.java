package serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
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


@SpringBootTest(classes = SpringConfig.class)
@ActiveProfiles("test")
@Transactional
public class UserServiceImplTest {

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
    public void testGetUserDTOById() {
        int userId = 1;
        String userName = "userName";
        UserDTO userDTO = userService.getUserDTOById(userId);
        assertEquals(userName, userDTO.getUserName());
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        String userName = "userName";
        User user = userService.getUserById(userId);
        assertEquals(userName, user.getUserName());
    }

    @Test
    public void testGetAllUsersWithPagination() {
        List<UserDTO> users = userService.getAllUsersWithPagination(1);
        assertEquals(1, users.size());
    }

    @Test
    public void testCreateUser() {
        User user = User.builder()
                .userName("secondUser")
                .build();
        userService.createUser(user);
        List<UserDTO> users = userService.getAllUsersWithPagination(1);
        assertEquals(2, users.size());

        User createdUser = userService.getUserById(2);
        assertEquals("secondUser", createdUser.getUserName());
    }

    @Test
    public void testGetUserWithHighestCostOfAllOrders() {
        User newUser = User.builder()
                .userName("secondUser")
                .build();
        userService.createUser(newUser);
        User user = userService.getUserWithHighestCostOfAllOrders();
        assertEquals(1, user.getId());
    }
}