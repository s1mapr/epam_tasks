package serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void testCreateTag() {
        when(tagRepository.save(any())).thenReturn(Tag.builder().id(1).name("Test Tag").build());

        int result = tagService.createTag("Test Tag");

        assertEquals(1, result);
    }

    @Test
    public void testDeleteTagById() {
        doNothing().when(tagRepository).deleteTagById(1);

        assertDoesNotThrow(() -> tagService.deleteTagById(1));
        verify(tagRepository, times(1)).deleteTagById(1);
    }

    @Test
    public void testGetOptionalTagByName() {
        Tag tag = new Tag();
        when(tagRepository.getTagByName("Test Tag")).thenReturn(Optional.of(tag));

        Optional<Tag> result = tagService.getOptionalTagByName("Test Tag");

        assertTrue(result.isPresent());
        assertEquals(tag, result.get());
    }

    @Test
    public void testGetTagByName() {
        Tag tag = new Tag();
        tag.setName("Test Tag");
        when(tagRepository.getTagByName("Test Tag")).thenReturn(Optional.of(tag));

        TagDTO result = tagService.getTagByName("Test Tag");

        assertEquals(tag.getName(), result.getName());
    }

    @Test
    public void testGetTagById() {
        Tag tag = new Tag();
        tag.setId(1);
        when(tagRepository.getTagById(1)).thenReturn(Optional.of(tag));

        Tag result = tagService.getTagById(1);

        assertEquals(tag, result);
    }

}