package serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUserDTOById() {
        User user = User.builder().id(1).build();

        when(userRepository.getUserById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserDTOById(1);

        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetUserById() {
        User user = User.builder().id(1).build();

        when(userRepository.getUserById(1)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1);

        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetAllUsersWithPagination() {
        User user = User.builder().id(1).build();


        when(userRepository.findAll(PageRequest.of(1, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(user)));

        List<UserDTO> result = userService.getAllUsersWithPagination(1);

        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
    }

    @Test
    public void testCreateUser() {
        User user = User.builder().id(1).build();

        when(userRepository.save(any())).thenReturn(user);

        int result = userService.createUser(user);

        assertEquals(user.getId(), result);
    }

    @Test
    public void testGetUserByUserName() {
        User user = User.builder().id(1).build();

        when(userRepository.getUserByUserName("test")).thenReturn(Optional.of(user));

        User result = userService.getUserByUserName("test");

        assertEquals(user.getId(), result.getId());
    }

}