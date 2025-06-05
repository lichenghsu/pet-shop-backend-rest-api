package com.lichenghsu.petshop.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String productName;
    private int quantity;
    private double price;
}
