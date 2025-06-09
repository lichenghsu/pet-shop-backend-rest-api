package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemResponse {

    @Schema(description = "商品名稱", example = "狗飼料")
    private String productName;

    @Schema(description = "購買數量", example = "2")
    private int quantity;

    @Schema(description = "單項總價", example = "998.0")
    private double price;
}
