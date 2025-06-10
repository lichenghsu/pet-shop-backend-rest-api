package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品回應")
public class ProductResponse {

    @Schema(description = "商品 ID")
    private Long id;

    @Schema(description = "商品名稱")
    private String name;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品價格")
    private double price;

    @Schema(description = "圖片 URL 清單")
    private List<String> imageUrls;

    @Schema(description = "分類名稱")
    private String categoryName;

    @Schema(description = "標籤名稱清單")
    private List<String> tagNames;
}
