package com.lichenghsu.petshop.dto;

import com.lichenghsu.petshop.enums.OrderStatus;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;

}