package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.AdminOrderResponse;
import com.lichenghsu.petshop.dto.OrderResponse;
import com.lichenghsu.petshop.dto.UpdateOrderStatusRequest;
import com.lichenghsu.petshop.enums.OrderStatus;
import com.lichenghsu.petshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {

    private final OrderService orderService;

//    // 管理員依狀態查詢訂單
//    @GetMapping("/status/{status}")
//    @Transactional
//    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus status) {
//        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
//    }

    // 支援查詢全部訂單或依狀態篩選（使用 status query parameter）
    @GetMapping
    @Transactional
    public ResponseEntity<List<OrderResponse>> queryOrders(
            @RequestParam(required = false) OrderStatus status
    ) {
        if (status != null) {
            return ResponseEntity.ok(orderService.getOrdersByStatus(status));
        } else {
            return ResponseEntity.ok(orderService.getAllOrders());
        }
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<AdminOrderResponse> getAdminOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAdminOrderDetails(id));
    }

    // 取消訂單(狀態變爲： CANCEL)
    @PatchMapping("/{id}/cancel")
    @Transactional
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // 管理員更新訂單狀態
    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.getStatus()));
    }
}