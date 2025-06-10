package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品建立請求")
public class ProductRequest {

    @Schema(description = "商品名稱", example = "狗飼料")
    private String name;

    @Schema(description = "商品描述", example = "高品質狗飼料")
    private String description;

    @Schema(description = "價格", example = "499.0")
    private double price;

    @Schema(description = "圖片 ID 清單")
    private List<Long> imageIds;

    @Schema(description = "分類 ID", example = "1")
    private Long categoryId;

    @Schema(description = "標籤 ID 清單", example = "[1, 2]")
    private List<Long> tagIds;
}
