package serviceTest;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.service.GiftCertificateService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
@Transactional
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

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
    void createGiftCertificateTest() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        Integer id = giftCertificateService.createGiftCertificate(giftCertificate);
        assertNotNull(id);
    }

    @Test
    void getAllGiftCertificatesTest() {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAllGiftCertificates();
        assertNotNull(giftCertificates);
    }

    @Test
    void getGiftCertificateByIdTest() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        Integer id = giftCertificateService.createGiftCertificate(giftCertificate);
        assertNotNull(id);

        GiftCertificate foundGiftCertificate = giftCertificateService.getGiftCertificateById(id);
        assertNotNull(foundGiftCertificate);
        assertEquals(foundGiftCertificate.getId(), id);
    }

    @Test
    void deleteGiftCertificateByIdTest() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        Integer id = giftCertificateService.createGiftCertificate(giftCertificate);
        assertNotNull(id);

        giftCertificateService.deleteGiftCertificateById(id);
        assertThrows(BadRequestException.class, () -> giftCertificateService.getGiftCertificateById(id));
    }

    @Test
    void updateGiftCertificateTest() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("test")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        Integer id = giftCertificateService.createGiftCertificate(giftCertificate);
        assertNotNull(id);

        GiftCertificate updatedGiftCertificate = GiftCertificate.builder()
                .name("test")
                .description("description")
                .price(100.0)
                .duration(30)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        giftCertificateService.updateGiftCertificate(updatedGiftCertificate, id);
        GiftCertificate foundGiftCertificate = giftCertificateService.getGiftCertificateById(id);
        assertNotNull(foundGiftCertificate);
        assertEquals(foundGiftCertificate.getName(), updatedGiftCertificate.getName());
        assertEquals(foundGiftCertificate.getDescription(), updatedGiftCertificate.getDescription());
        assertEquals(foundGiftCertificate.getPrice(), updatedGiftCertificate.getPrice());
        assertEquals(foundGiftCertificate.getDuration(), updatedGiftCertificate.getDuration());
    }
}
