package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.DashboardResponse;
import com.lichenghsu.petshop.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "後台儀表板", description = "提供管理者後台統計資訊 API")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "取得後台統計數據",
            description = "包含商品數量、會員數量與總營收金額"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得統計資料",
            content = @Content(schema = @Schema(implementation = DashboardResponse.class))
    )
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }
}
