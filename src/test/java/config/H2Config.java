package config;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.dao.mysql.impl.MySQLGiftCertificateDAO;
import com.epam.esm.dao.mysql.impl.MySQLTagDAO;
import com.epam.esm.dao.mysql.impl.MySQLTagGiftDAO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagGiftService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class H2Config {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:create.sql", "classpath:insert.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public TagDAO tagDAO() {
        return new MySQLTagDAO(jdbcTemplate());
    }

    @Bean
    public GiftCertificateDAO giftCertificateDAO() {
        return new MySQLGiftCertificateDAO(jdbcTemplate());
    }

    @Bean
    public TagService tagService() {
        return new TagServiceImpl(tagDAO());
    }

    @Bean
    public GiftCertificateService giftCertificateService(){
        return new GiftCertificateServiceImpl(giftCertificateDAO());
    }

    @Bean
    public TagGiftDAO tagGiftDAO(){
        return new MySQLTagGiftDAO(jdbcTemplate());
    }

    @Bean
    public TagGiftService tagGiftService(){
        return new TagGiftServiceImpl(tagGiftDAO(), tagService(), giftCertificateService());
    }
}
