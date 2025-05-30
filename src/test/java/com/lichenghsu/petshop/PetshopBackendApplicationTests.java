package com.lichenghsu.petshop;

import com.lichenghsu.petshop.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;

@ActiveProfiles("test")
@SpringBootTest(classes = { PetshopBackendApplication.class, TestConfig.class })
class PetshopBackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("測試啟動中...");
    }
}

