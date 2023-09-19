package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.epam.esm.entity")
@ComponentScan("com.epam.esm")
public class ApplicationMain extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class);
    }
}
