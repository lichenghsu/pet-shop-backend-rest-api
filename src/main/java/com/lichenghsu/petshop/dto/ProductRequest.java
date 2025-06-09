package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "新增或更新商品所需資料")
public class ProductRequest {

    @Schema(description = "商品名稱", example = "狗飼料")
    private String name;

    @Schema(description = "商品描述", example = "適合中大型犬的全齡狗飼料")
    private String description;

    @Schema(description = "商品價格", example = "499.0")
    private double price;

    @Schema(description = "圖片 ID 清單", example = "[1, 2]")
    private List<Long> imageIds;
}
