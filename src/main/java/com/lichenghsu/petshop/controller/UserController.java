package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.UserInfoResponse;
import com.lichenghsu.petshop.entity.User;
import com.lichenghsu.petshop.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "使用者資訊", description = "登入後的個人資訊 API")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @Operation(summary = "取得目前登入使用者資訊", description = "根據 JWT token 取得使用者資訊")
    public ResponseEntity<UserInfoResponse> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserInfoResponse response = new UserInfoResponse(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response);
    }
}
