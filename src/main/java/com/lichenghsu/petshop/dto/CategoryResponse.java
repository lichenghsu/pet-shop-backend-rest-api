package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    @Schema(description = "類別編號", example = "1")
    private Long id;

    @Schema(description = "類別名稱", example = "狗飼料")
    private String name;
}