package com.dankan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DankanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DankanApplication.class, args);
    }
}
