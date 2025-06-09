package com.lichenghsu.petshop.dto;

import com.lichenghsu.petshop.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "使用者訂單回應資料")
public class OrderResponse {

    @Schema(description = "訂單 ID", example = "1001")
    private Long id;

    @Schema(description = "訂單狀態", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "訂單建立時間", example = "2025-06-08T12:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "訂單商品清單")
    private List<OrderItemDto> items;

    @Data
    @Schema(description = "訂單商品明細")
    public static class OrderItemDto {

        @Schema(description = "商品名稱", example = "狗飼料")
        private String productName;

        @Schema(description = "購買數量", example = "2")
        private int quantity;

        @Schema(description = "價格總計", example = "998.0")
        private double price;
    }
}
