package com.lichenghsu.petshop.config;

import com.lichenghsu.petshop.entity.Role;
import com.lichenghsu.petshop.entity.User;
import com.lichenghsu.petshop.repository.RoleRepository;
import com.lichenghsu.petshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 初始化角色
        if (!roleRepository.existsById("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (!roleRepository.existsById("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
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
            System.out.println("✅ 預設管理員帳號建立完成：帳號 admin / 密碼 admin123");
        }
    }
}

