package com.hes.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = JacksonAutoConfiguration.class)
public class UsersServiceApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(com.hes.test.UsersServiceApp.class, args);
    }
}
