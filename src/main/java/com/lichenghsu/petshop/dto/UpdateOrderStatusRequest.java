package com.lichenghsu.petshop.dto;

import com.lichenghsu.petshop.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "更新訂單狀態請求")
public class UpdateOrderStatusRequest {


    @Schema(description = "多比訂單 ID")
    private List<Long> orderIds;

    @Schema(
            description = "新的訂單狀態",
            example = "SHIPPED"
    )
    private OrderStatus status;

}
