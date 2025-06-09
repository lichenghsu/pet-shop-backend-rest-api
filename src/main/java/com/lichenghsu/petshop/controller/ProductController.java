package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.ProductRequest;
import com.lichenghsu.petshop.dto.ProductResponse;
import com.lichenghsu.petshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "前台商品檢視與後台商品操作 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "取得所有商品", description = "列出所有商品，無需登入")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "新增商品", description = "僅限管理員新增商品")
    @ApiResponse(responseCode = "200", description = "新增成功",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @Operation(summary = "查詢單一商品", description = "根據商品 ID 查詢商品資料")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "刪除商品", description = "僅限管理員刪除商品")
    @ApiResponse(responseCode = "204", description = "刪除成功")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
