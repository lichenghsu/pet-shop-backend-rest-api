package com.lichenghsu.petshop.dto;

import com.lichenghsu.petshop.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;

    @Data
    public static class OrderItemDto {
        private String productName;
        private int quantity;
        private double price;
    }
}
