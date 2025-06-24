package com.lichenghsu.petshop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理員查詢訂單回應")
public class AdminOrderResponse {

    @Schema(description = "訂單 ID", example = "1")
    private Long id;

    @Schema(description = "訂單狀態", example = "SHIPPED")
    private String status;

    @Schema(description = "建立時間", example = "2025-06-08T12:51:49.063232")
    private LocalDateTime createdAt;

    @Schema(description = "使用者 ID", example = "1")
    private Long userId;

    @Schema(description = "使用者帳號", example = "admin")
    private String username;

    @Schema(description = "使用者 Email", example = "admin@example.com")
    private String email;

    @Schema(description = "訂單項目")
    private List<OrderItemDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "訂單項目")
    public static class OrderItemDto {
        @Schema(description = "商品名稱", example = "狗飼料")
        private String productName;

        @Schema(description = "數量", example = "2")
        private int quantity;

        @Schema(description = "小計價格", example = "998.0")
        private double price;
    }
}
