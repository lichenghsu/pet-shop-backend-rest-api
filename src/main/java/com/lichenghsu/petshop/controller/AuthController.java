package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.AuthRequest;
import com.lichenghsu.petshop.dto.AuthResponse;
import com.lichenghsu.petshop.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "用戶驗證", description = "提供用戶登入與註冊的 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "用戶登入", description = "使用者登入並取得 JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登入成功"),
            @ApiResponse(responseCode = "401", description = "帳號或密碼錯誤")
    })
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "用戶註冊", description = "註冊新用戶，預設角色為 USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "註冊成功"),
            @ApiResponse(responseCode = "400", description = "使用者名稱已存在")
    })
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        authService.register(request);
        return ResponseEntity.ok("註冊成功");
    }
}
