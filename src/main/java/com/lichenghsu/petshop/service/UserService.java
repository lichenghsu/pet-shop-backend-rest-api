package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.entity.Role;
import com.lichenghsu.petshop.entity.User;
import com.lichenghsu.petshop.dto.UserResponse;
import com.lichenghsu.petshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setEnabled(user.isEnabled());
            dto.setRole(user.getRoles().stream()
                    .findFirst().map(Role::getName).orElse("USER"));
            return dto;
        }).collect(Collectors.toList());
    }

    public void toggleUserActive(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到會員：" + userId));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    public void setUserActive(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到會員：" + userId));
        user.setEnabled(active);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}