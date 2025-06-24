package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MonthlyRevenueItem {

    @Schema(description = "月份（格式：yyyy-MM）", example = "2025-06")
    private String month;

    @Schema(description = "該月總營收", example = "15000.0")
    private Double total;
}