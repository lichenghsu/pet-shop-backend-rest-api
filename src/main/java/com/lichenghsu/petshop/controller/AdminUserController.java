package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.UserResponse;
import com.lichenghsu.petshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "會員管理", description = "後台管理會員的 API")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "取得所有會員資料")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> updateUserActive(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        boolean isActive = body.get("isActive");
        userService.setUserActive(id, isActive);
        return ResponseEntity.ok().build();
    }
}