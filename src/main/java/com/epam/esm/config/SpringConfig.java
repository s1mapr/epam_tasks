package com.epam.esm.config;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.dao.mysql.impl.MySQLGiftCertificateDAO;
import com.epam.esm.dao.mysql.impl.MySQLTagDAO;
import com.epam.esm.dao.mysql.impl.MySQLTagGiftDAO;
import com.epam.esm.dao.postgresql.impl.PostgreSQLGiftCertificateDAO;
import com.epam.esm.dao.postgresql.impl.PostgreSQLTagDAO;
import com.epam.esm.dao.postgresql.impl.PostgreSQLTagGiftDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-${spring.profiles.active}.properties")})
public class SpringConfig {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;


    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        return dataSource;
    }


    @Profile("dev")
    @Bean(name = "tagDAO")
    public TagDAO mySQLTagDAO() {
        return new MySQLTagDAO(jdbcTemplate(dataSource()));
    }

    @Profile("prod")
    @Bean(name = "tagDAO")
    public TagDAO postgreSQLTagDAO() {
        return new PostgreSQLTagDAO(jdbcTemplate(dataSource()));
    }

    @Profile("dev")
    @Bean(name = "giftCertificateDAO")
    public GiftCertificateDAO mySQLGiftCertificateDAO() {
        return new MySQLGiftCertificateDAO(jdbcTemplate(dataSource()));
    }

    @Profile("prod")
    @Bean(name = "giftCertificateDAO")
    public GiftCertificateDAO postrgreSQLGiftCertificateDAO() {
        return new PostgreSQLGiftCertificateDAO(jdbcTemplate(dataSource()));
    }

    @Profile("dev")
    @Bean("tagGiftDAO")
    public TagGiftDAO mySQLTagGiftDAO() {
        return new MySQLTagGiftDAO(jdbcTemplate(dataSource()));
    }

    @Profile("prod")
    @Bean("tagGiftDAO")
    public TagGiftDAO postgreSQLTagGiftDAO() {
        return new PostgreSQLTagGiftDAO(jdbcTemplate(dataSource()));
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
