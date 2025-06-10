package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewRequest {

    @Schema(description = "商品 ID", example = "1")
    private Long productId;

    @Schema(description = "用戶名稱", example = "小明")
    private String username;

    @Schema(description = "評分（1-5）", example = "5")
    private int rating;

    @Schema(description = "評論內容", example = "很好用的商品！")
    private String comment;
}
