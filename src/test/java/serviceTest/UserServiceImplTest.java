//package serviceTest;
//
//import com.epam.esm.config.SpringConfig;
//import com.epam.esm.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//
//@SpringBootTest
//@ContextConfiguration(classes = SpringConfig.class)
//@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//public class UserServiceImplTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testGetUserDTOById() {
//        // Тестирование логики получения UserDTO по ID
//        // ... (вы можете добавить свои тестовые сценарии и утверждения)
//    }
//
//    @Test
//    public void testGetUserById() {
//        // Тестирование логики получения User по ID
//        // ... (вы можете добавить свои тестовые сценарии и утверждения)
//    }
//
//    @Test
//    public void testGetAllUsersWithPagination() {
//        // Тестирование логики получения списка UserDTO с пагинацией
//        // ... (вы можете добавить свои тестовые сценарии и утверждения)
//    }
//
//    @Test
//    public void testCreateUser() {
//        // Тестирование логики создания пользователя
//        // ... (вы можете добавить свои тестовые сценарии и утверждения)
//    }
//
//    @Test
//    public void testGetUserWithHighestCostOfAllOrders() {
//        // Тестирование логики получения пользователя с наибольшей стоимостью всех заказов
//        // ... (вы можете добавить свои тестовые сценарии и утверждения)
//    }
//}