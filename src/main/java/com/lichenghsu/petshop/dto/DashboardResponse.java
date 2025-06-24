package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class DashboardResponse {

    @Schema(description = "商品數量")
    private Long productCount;

    @Schema(description = "用戶數量")
    private Long userCount;

    @Schema(description = "總訂單金額")
    private Double totalOrderAmount;

    @Schema(description = "每月營收")
    private List<MonthlyRevenueItem> monthlyRevenue;
}