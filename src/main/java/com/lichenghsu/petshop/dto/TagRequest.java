package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "標籤請求資料")
public class TagRequest {

    @Schema(description = "標籤名稱", example = "限時優惠")
    private String name;
}
