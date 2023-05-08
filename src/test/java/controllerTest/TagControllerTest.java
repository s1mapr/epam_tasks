package controllerTest;

import com.epam.esm.controller.TagController;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.H2Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {H2Config.class})
public class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagServiceImpl tagServiceImpl;
    @Mock
    private TagGiftServiceImpl tagGiftService;

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

        doNothing().when(tagServiceImpl).createTag(tag.getName());

        mockMvc.perform(MockMvcRequestBuilders.post("/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tag)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTags() throws Exception {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());

        when(tagServiceImpl.getAllTags()).thenReturn(tagList);

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
        Tag tag = new Tag();
        tag.setId(id);

        when(tagServiceImpl.getTagById(id)).thenReturn(tag);

        mockMvc.perform(MockMvcRequestBuilders.get("/tag/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

    }
}
