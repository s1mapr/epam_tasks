package com.epam.esm.config;


import com.epam.esm.dao.*;
import com.epam.esm.dao.mysql.impl.*;
import com.epam.esm.dao.postgresql.impl.*;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-${spring.profiles.active}.properties")})
public class SpringConfig {


    @Profile("test")
    @Bean
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }


    @Profile("dev")
    @Bean(name = "tagDAO")
    public TagDAO mySQLTagDAO() {
        System.out.println("dev");
        return new MySQLTagDAO();
    }

    @Profile("test")
    @Bean(name = "tagDAO")
    public TagDAO testMySQLTagDAO() {
        System.out.println("test");
        return new MySQLTagDAO();
    }

    @Profile("prod")
    @Bean(name = "tagDAO")
    public TagDAO postgreSQLTagDAO() {
        System.out.println("prod");
        return new PostgreSQLTagDAO();
    }

    @Profile("dev")
    @Bean(name = "giftCertificateDAO")
    public GiftCertificateDAO mySQLGiftCertificateDAO() {
        return new MySQLGiftCertificateDAO();
    }

    @Profile("test")
    @Bean(name = "giftCertificateDAO")
    public GiftCertificateDAO testMySQLGiftCertificateDAO() {
        return new MySQLGiftCertificateDAO();
    }

    @Profile("prod")
    @Bean(name = "giftCertificateDAO")
    public GiftCertificateDAO postrgreSQLGiftCertificateDAO() {
        return new PostgreSQLGiftCertificateDAO();
    }

    @Profile("dev")
    @Bean("tagGiftDAO")
    public TagGiftDAO mySQLTagGiftDAO() {
        return new MySQLTagGiftDAO();
    }

    @Profile("test")
    @Bean("tagGiftDAO")
    public TagGiftDAO testMySQLTagGiftDAO() {
        return new MySQLTagGiftDAO();
    }

    @Profile("prod")
    @Bean("tagGiftDAO")
    public TagGiftDAO postgreSQLTagGiftDAO() {
        return new PostgreSQLTagGiftDAO();
    }

    @Profile("dev")
    @Bean("userDAO")
    public UserDAO mySQLUserDAO() {
        return new MySQLUserDAO();
    }

    @Profile("test")
    @Bean("userDAO")
    public UserDAO testMySQLUserDAO() {
        return new MySQLUserDAO();
    }

    @Profile("prod")
    @Bean("userDAO")
    public UserDAO postgreSQLUserDAO() {
        return new PostgreSQLUserDAO();
    }

    @Profile("dev")
    @Bean("orderDAO")
    public OrderDAO mySQLOrderDAO() {
        return new MySQLOrderDAO();
    }

    @Profile("test")
    @Bean("orderDAO")
    public OrderDAO testMySQLOrderDAO() {
        return new MySQLOrderDAO();
    }

    @Profile("prod")
    @Bean("orderDAO")
    public OrderDAO postgreSQLOrderDAO() {
        return new PostgreSQLOrderDAO();
    }

}
