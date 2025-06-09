package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    @Schema(description = "訂單商品列表", required = true)
    private List<OrderItemRequest> items;
}
