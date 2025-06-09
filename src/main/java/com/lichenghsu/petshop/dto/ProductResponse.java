package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品查詢回傳資料")
public class ProductResponse {

    @Schema(description = "商品 ID", example = "1")
    private Long id;

    @Schema(description = "商品名稱", example = "狗飼料")
    private String name;

    @Schema(description = "商品描述", example = "優質蛋白質來源")
    private String description;

    @Schema(description = "價格", example = "499.0")
    private double price;

    @Schema(description = "商品圖片連結列表", example = "[\"/api/images/1\", \"/api/images/2\"]")
    private List<String> imageUrls;
}
