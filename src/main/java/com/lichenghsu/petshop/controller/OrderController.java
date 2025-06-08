package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.*;
import com.lichenghsu.petshop.service.OrderService;
import jakarta.transaction.Transactional;
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

    // 使用者建立訂單
    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(orderService.placeOrder(request, authentication));
    }

    // 利用訂單編號查詢
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(orderService.getOrderById(id, authentication));
    }

    // 使用者查詢自己的訂單
    @GetMapping
    @Transactional
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }

    // 更新訂單中商品
    @PutMapping("/{id}/items")
    @Transactional
    public ResponseEntity<OrderResponse> updateItems(@PathVariable Long id, @RequestBody List<OrderItemRequest> items) {
        return ResponseEntity.ok(orderService.updateOrderItems(id, items));
    }
}
