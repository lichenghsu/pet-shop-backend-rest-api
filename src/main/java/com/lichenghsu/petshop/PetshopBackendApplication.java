package com.lichenghsu.petshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PetshopBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetshopBackendApplication.class, args);
    }
}

