package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.OrderRequest;
import com.lichenghsu.petshop.dto.OrderResponse;
import com.lichenghsu.petshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 🔸 下訂單
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.placeOrder(request, authentication));
    }

    // 🔸 查詢登入使用者的訂單
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }
}
