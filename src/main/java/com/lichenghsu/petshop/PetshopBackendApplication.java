package com.lichenghsu.petshop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(
        info = @Info(title = "Pet Shop API", version = "1.0", description = "API for managing pet shop")
)
@SpringBootApplication
@EnableCaching
public class PetshopBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetshopBackendApplication.class, args);
    }
}

