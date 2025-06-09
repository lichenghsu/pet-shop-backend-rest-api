package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.*;
import com.lichenghsu.petshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "前台訂單管理", description = "一般使用者訂單操作 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "建立訂單", description = "使用者可送出訂單，並將目前購物車內容轉為訂單資料")
    @ApiResponse(responseCode = "200", description = "訂單建立成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(orderService.placeOrder(request, authentication));
    }

    @Operation(summary = "查詢單筆訂單", description = "根據訂單編號查詢，僅限該使用者本人")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(orderService.getOrderById(id, authentication));
    }

    @Operation(summary = "查詢我的所有訂單", description = "列出目前登入使用者的所有訂單")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class))))
    @GetMapping
    @Transactional
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }

    @Operation(summary = "更新訂單商品內容", description = "修改指定訂單的商品清單與數量")
    @ApiResponse(responseCode = "200", description = "修改成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @PutMapping("/{id}/items")
    @Transactional
    public ResponseEntity<OrderResponse> updateItems(
            @PathVariable Long id,
            @RequestBody List<OrderItemRequest> items) {
        return ResponseEntity.ok(orderService.updateOrderItems(id, items));
    }
}
