package controllerTest;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.impl.UserOrderServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagServiceImpl tagServiceImpl;
    @Mock
    private TagGiftServiceImpl tagGiftService;
    @Mock
    private OrderService orderService;
    @Mock
    private UserService userService;

    @Mock
    private UserOrderServiceImpl userOrderService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void testCreateTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("Test tag");

        when(tagServiceImpl.createTag(tag.getName())).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tag)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTags() throws Exception {
        List<TagDTO> tagList = new ArrayList<>();
        tagList.add(TagDTO.builder().id(1).build());

        when(tagServiceImpl.getAllTagsWithPagination(null)).thenReturn(tagList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tag")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testDeleteTagById() throws Exception {
        int id = 1;

        doNothing().when(tagGiftService).deleteTagById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tag/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Deleted tag with id " + id));
    }

    @Test
    public void testGetTagById() throws Exception {
        int id = 1;
        TagDTO tag = TagDTO.builder().id(id).build();

        when(tagGiftService.getTagDTOById(id)).thenReturn(tag);

        mockMvc.perform(MockMvcRequestBuilders.get("/tag/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

    }


    @Test
    public void testGetMostWildlyUsedTagOfUserWithHighestCostOfAllOrders() throws Exception {
        User testUser = User.builder().id(1).build();

        List<Order> testOrders = new ArrayList<>();
        Order order1 = Order.builder().id(1).user(testUser).build();
        Order order2 = Order.builder().id(2).user(testUser).build();
        testOrders.add(order1);
        testOrders.add(order2);

        TagDTO testTag = TagDTO.builder().id(1).build();

        when(userOrderService.getUserWithHighestCostOfAllOrders()).thenReturn(testUser);
        when(orderService.getAllUserOrders(testUser)).thenReturn(testOrders);
        when(tagGiftService.getMostUsedTag(testOrders)).thenReturn(testTag);

        mockMvc.perform(MockMvcRequestBuilders.get("/tag/mostWildlyUsedTag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
