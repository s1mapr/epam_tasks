package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EntityScan("com.epam.esm.entity")
@ComponentScan("com.epam.esm")
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class);
    }
}
