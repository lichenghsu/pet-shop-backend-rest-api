package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderItemRequest {

    @NotNull
    @Schema(description = "產品 ID", example = "1", required = true)
    private Long productId;

    @Min(1)
    @Schema(description = "購買數量", example = "2", minimum = "1", required = true)
    private int quantity;
}
