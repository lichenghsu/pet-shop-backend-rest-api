package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ImageResponse {

    @Schema(description = "圖片 ID", example = "12")
    private Long id;

    @Schema(description = "圖片存取 URL", example = "/api/images/12")
    private String url;

    public ImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }
}
