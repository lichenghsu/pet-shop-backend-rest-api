package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.AdminOrderResponse;
import com.lichenghsu.petshop.dto.OrderResponse;
import com.lichenghsu.petshop.dto.UpdateOrderStatusRequest;
import com.lichenghsu.petshop.enums.OrderStatus;
import com.lichenghsu.petshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@io.swagger.v3.oas.annotations.tags.Tag(name = "後台訂單管理", description = "僅限管理員使用的訂單管理 API")
public class OrderAdminController {

    private final OrderService orderService;

    @Operation(summary = "查詢所有訂單或依狀態查詢", description = "可透過查詢參數 status 篩選指定狀態的訂單")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
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

    @Operation(summary = "取得指定訂單詳細資料", description = "管理員可查詢任一筆訂單的詳細內容，包括用戶名稱與信箱")
    @ApiResponse(responseCode = "200", description = "成功",
            content = @Content(schema = @Schema(implementation = AdminOrderResponse.class)))
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<AdminOrderResponse> getAdminOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAdminOrderDetails(id));
    }

    @Operation(summary = "取消訂單", description = "將訂單狀態設為 CANCELLED。僅限狀態為 PENDING 或 PAID 的訂單可取消。")
    @ApiResponse(responseCode = "200", description = "取消成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @PatchMapping("/{id}/cancel")
    @Transactional
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @Operation(summary = "更新訂單狀態", description = "管理員可變更訂單狀態（例如：PAID、SHIPPED、COMPLETED）")
    @ApiResponse(responseCode = "200", description = "狀態更新成功",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.getStatus()));
    }
}
