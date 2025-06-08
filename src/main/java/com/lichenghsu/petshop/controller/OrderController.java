package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.*;
import com.lichenghsu.petshop.enums.OrderStatus;
import com.lichenghsu.petshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // 使用者查詢自己的訂單
    @GetMapping
    @Transactional
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }

    // 管理員查詢所有訂單
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    @Transactional
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 管理員依狀態查詢訂單
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    @Transactional
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // 管理員更新訂單狀態
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.getStatus()));
    }
}
