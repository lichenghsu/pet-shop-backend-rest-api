package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用戶登入請求資料")
public class UserLoginRequest {

    @Schema(description = "使用者帳號", example = "user123", required = true)
    private String username;

    @Schema(description = "使用者密碼", example = "password", required = true)
    private String password;
}
