package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "標籤回應資料")
public class TagResponse {

    @Schema(description = "標籤編號", example = "1")
    private Long id;

    @Schema(description = "標籤名稱", example = "限時優惠")
    private String name;
}
