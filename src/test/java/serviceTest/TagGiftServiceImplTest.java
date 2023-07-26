package serviceTest;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.SearchParams;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        tagService.getAllTagsWithPagination(1).forEach(t-> System.out.println("tag with id " + t.getId()));
        tagGiftService.getAllEntries().forEach(t-> System.out.println("tag id = " + t.getTag().getId() + " certificate id = " + t.getGiftCertificate().getId()));
        giftCertificateService.getAllGiftCertificates().forEach(t-> System.out.println("certificate with id " + t.getId()));

        Integer countOfGiftCertificateTags = tagGiftService.getTagsByCertificateId(id).size();

        assertNotNull(countOfGiftCertificateTags);
        assertEquals(2, countOfGiftCertificateTags);
    }

    @Test
    void updateGiftCertificateTest() {
//        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.builder()
//                .name("name")
//                .description("description")
//                .price(100.0)
//                .duration(30)
//                .createDate(LocalDate.now().toString())
//                .lastUpdateDate(LocalDate.now().toString())
//                .tags(List.of(new TagDTO(null, "first"), new TagDTO(null, "second")))
//                .build();
//        Integer id = tagGiftService.createGiftCertificate(giftCertificateDTO);
//
//        assertNotNull(id);

        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.createDTO(giftCertificateService.getGiftCertificateById(5));
        int id = giftCertificateDTO.getId();
        GiftCertificateDTO updatedGiftCertificateDTO = GiftCertificateDTO.builder()
                .name("name")
                .description("description")
                .lastUpdateDate(LocalDate.now().toString())
                //.tags(List.of(new TagDTO(null, "third")))
                .build();

        //tagGiftService.updateGiftCertificate(updatedGiftCertificateDTO, id);

        Integer countOfGiftCertificateTags = tagGiftService.getTagsByCertificateId(id).size();

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
