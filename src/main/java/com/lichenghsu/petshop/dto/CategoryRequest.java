package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用於新增分類的請求資料")
public class CategoryRequest {

    @Schema(description = "分類名稱", example = "狗飼料")
    private String name;
}
