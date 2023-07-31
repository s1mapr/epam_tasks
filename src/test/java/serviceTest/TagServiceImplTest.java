package serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.TagService;
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
import java.util.Optional;

@SpringBootTest(classes = SpringConfig.class)
@ActiveProfiles("test")
@Transactional
public class TagServiceImplTest {

    @Autowired
    private TagService service;

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
    public void getAllTagsTest(){
        List<TagDTO> listOfTags = service.getAllTagsWithPagination(1);
        assertNotNull(listOfTags);
        assertFalse(listOfTags.isEmpty());
        assertEquals(5, listOfTags.size());
    }

    @Test
    public void testCreateTag() {
        String tagName = "test tag";
        service.createTag(tagName);
        Optional<Tag> tag = service.getOptionalTagByName(tagName);
        assertTrue(tag.isPresent());
        assertEquals(tagName, tag.get().getName());
    }

    @Test
    public void testDeleteTagById() {
        int tagId = 1;
        assertThrows(BadRequestException.class, () -> {
            service.deleteTagById(tagId);
        });
    }

    @Test
    public void testGetOptionalTagByName() {
        String tagName = "base";
        Optional<Tag> tag = service.getOptionalTagByName(tagName);
        assertTrue(tag.isPresent());
        assertEquals(tagName, tag.get().getName());
    }
    @Test
    public void testGetTagByName() {
        String tagName = "base";
        TagDTO tag = service.getTagByName(tagName);
        assertEquals(tagName, tag.getName());
    }

    @Test
    public void testGetTagById() {
        int tagId = 4;
        Tag tag = service.getTagById(tagId);
        assertNotNull(tag);
        assertEquals(tagId, tag.getId());
    }

}