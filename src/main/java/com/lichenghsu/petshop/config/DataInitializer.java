package com.lichenghsu.petshop.config;

import com.lichenghsu.petshop.entity.Category;
import com.lichenghsu.petshop.entity.Role;
import com.lichenghsu.petshop.entity.Tag;
import com.lichenghsu.petshop.entity.User;
import com.lichenghsu.petshop.repository.CategoryRepository;
import com.lichenghsu.petshop.repository.RoleRepository;
import com.lichenghsu.petshop.repository.TagRepository;
import com.lichenghsu.petshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {
        // 初始化角色
        if (!roleRepository.existsById("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
            logger.info("ROLE_USER created.");
        }
        if (!roleRepository.existsById("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            logger.info("ROLE_ADMIN created.");
        }

        // 初始化管理員帳號
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findById("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("初始化失敗：找不到 ROLE_ADMIN"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));

            userRepository.save(admin);
            logger.info("預設管理員帳號建立完成：帳號 admin / 密碼 admin123");
        } else {
            logger.info("管理員帳號已存在，跳過建立。");
        }

        // 初始化分類
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "測試分類 1"));
            categoryRepository.save(new Category(null, "測試分類 2"));

            logger.info("測試分類建立成功");
        } else {
            logger.info("測試分類已存在，跳過建立。");
        }

        // 初始化標籤
        if (tagRepository.count() == 0) {
            tagRepository.save(new Tag(null, "測試標籤 1"));
            tagRepository.save(new Tag(null, "測試標籤 2"));

            logger.info("測試標籤建立成功");
        } else {
            logger.info("測試標籤已存在，跳過建立。");
        }
    }
}

