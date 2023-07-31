package serviceTest;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.*;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.*;
import com.epam.esm.dto.SearchParams;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringConfig.class)
@ActiveProfiles("test")
@Transactional
public class TagGiftServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagGiftService tagGiftService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void reset() {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("create.sql"));
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("insert.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createGiftCertificateWithNotExistingTagsTest() {
        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.builder()
                .name("name")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDate.now().toString())
                .lastUpdateDate(LocalDate.now().toString())
                .tags(List.of(new TagDTO(null, "first"), new TagDTO(null, "second")))
                .build();


        Integer id = tagGiftService.createGiftCertificate(giftCertificateDTO);

        assertNotNull(id);

        Integer countOfGiftCertificateTags = tagGiftService.getTagsByCertificateId(id).size();

        assertNotNull(countOfGiftCertificateTags);
        assertEquals(2, countOfGiftCertificateTags);
    }

    @Test
    void updateGiftCertificateTest() {
        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.builder()
                .name("name")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDate.now().toString())
                .lastUpdateDate(LocalDate.now().toString())
                .tags(List.of(new TagDTO(null, "first"), new TagDTO(null, "second")))
                .build();
        Integer id = tagGiftService.createGiftCertificate(giftCertificateDTO);

        assertNotNull(id);


        GiftCertificateDTO updatedGiftCertificateDTO = GiftCertificateDTO.builder()
                .name("name")
                .description("description")
                .lastUpdateDate(LocalDate.now().toString())
                .tags(List.of(new TagDTO(null, "third")))
                .build();

        tagGiftService.updateGiftCertificate(updatedGiftCertificateDTO, id);

        Integer countOfGiftCertificateTags = tagGiftService.getTagsByCertificateId(id).size();

        assertNotNull(countOfGiftCertificateTags);
        assertEquals(3, countOfGiftCertificateTags);

        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificateById(id);
        assertEquals(30, giftCertificate.getDuration());

    }


    @Test
    void testGetCertificatesByFilterTest() {
        SearchParams searchParams = SearchParams.builder()
                .name("test2")
                .build();
        List<GiftCertificateDTO> giftCertificateDTOS = tagGiftService.getCertificatesBySearchParams(searchParams);
        assertEquals(1, giftCertificateDTOS.size());
        assertEquals("test2", giftCertificateDTOS.get(0).getName());
    }

    @Test
    void testGetAllGiftCertificateDTOWithPagination() {
        List<GiftCertificateDTO> list = tagGiftService.getAllGiftCertificateDTOWithPagination(1);
        assertEquals(8, list.size());
    }

    @Test
    void testGetCertificateDTOById(){
        int certificateId = 5;
        String certificateName = "test4";
        GiftCertificateDTO giftCertificateDTO = tagGiftService.getCertificateDTOById(certificateId);
        assertEquals(certificateName, giftCertificateDTO.getName());
        assertEquals(2, giftCertificateDTO.getTags().size());
    }

    @Test
    void testDeleteTagById(){
        int tagId = 4;
        tagGiftService.deleteTagById(tagId);
        assertEquals(4, tagService.getAllTagsWithPagination(1).size());
        assertThrows(BadRequestException.class, () -> {
            tagGiftService.deleteTagById(tagId);
        });
    }

    @Test
    void testDeleteCertificateById(){
        int certificateId = 6;
        tagGiftService.deleteCertificateById(certificateId);
        assertEquals(7, giftCertificateService.getAllGiftCertificates().size());
    }

    @Test
    void testGetTagDTOById(){
        int tagId = 4;
        String tagName = "300$";
        TagDTO tagDTO = tagGiftService.getTagDTOById(tagId);
        assertEquals(tagName ,tagDTO.getName());
    }

    @Test
    void testGetMostUsedTag(){
        User user = userService.getUserById(1);
        List<Order> orders = orderService.getAllUserOrders(user);
        TagDTO tag = tagGiftService.getMostUsedTag(orders);
        assertEquals(4, tag.getId());
    }

    @Test
    void testGetGiftCertificatesByTagId(){
        int tagId = 4;
        List<GiftCertificate> giftCertificates = tagGiftService.getGiftCertificatesByTagId(tagId);
        assertEquals(2, giftCertificates.size());
    }

    @Test
    void testGetAllEntries(){
        List<TagGift> tagGifts = tagGiftService.getAllEntries();
        assertEquals(4, tagGifts.size());
    }
}
