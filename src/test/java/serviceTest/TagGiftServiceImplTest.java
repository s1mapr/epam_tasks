package serviceTest;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.SearchParams;
import config.H2Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
@Transactional
public class TagGiftServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagGiftService tagGiftService;

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
                .tags(List.of(new Tag(null, "first"), new Tag(null, "second")))
                .build();
        Integer id = tagGiftService.createGiftCertificate(giftCertificateDTO);

        assertNotNull(id);

        Integer countOfGiftCertificateTags = tagService.getTagsByCertificateId(id).size();

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
                .tags(List.of(new Tag(null, "first"), new Tag(null, "second")))
                .build();
        Integer id = tagGiftService.createGiftCertificate(giftCertificateDTO);

        assertNotNull(id);

        GiftCertificateDTO updatedGiftCertificateDTO = GiftCertificateDTO.builder()
                .name("name")
                .description("description")
                .lastUpdateDate(LocalDate.now().toString())
                .tags(List.of(new Tag(null, "third")))
                .build();

        tagGiftService.updateGiftCertificate(updatedGiftCertificateDTO, id);

        Integer countOfGiftCertificateTags = tagService.getTagsByCertificateId(id).size();

        assertNotNull(countOfGiftCertificateTags);
        assertEquals(3, countOfGiftCertificateTags);

        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificateById(id);
        assertEquals(30, giftCertificate.getDuration());

    }



    @Test
    void getCertificatesByFilterTest(){
        SearchParams searchParams = SearchParams.builder()
                .name("test2")
                .build();
        List<GiftCertificateDTO> giftCertificateDTOS= tagGiftService.getCertificatesBySearchParams(searchParams);
        assertEquals(1, giftCertificateDTOS.size());
        assertEquals("test2", giftCertificateDTOS.get(0).getName());
    }
}
