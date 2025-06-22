package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.ProductRequest;
import com.lichenghsu.petshop.dto.ProductResponse;
import com.lichenghsu.petshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "提供商品查詢與管理（需登入與管理員權限）")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "查詢所有商品", description = "開放給所有使用者查詢商品列表")
    @ApiResponse(responseCode = "200", description = "成功取得商品列表",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        log.info("取得所有商品");
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "查詢單一商品", description = "根據商品 ID 查詢詳細內容")
    @ApiResponse(responseCode = "200", description = "查詢成功",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        log.info("查詢商品 ID = {}", id);
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "建立商品（限管理員）", description = "建立新商品，需附上分類 ID、圖片 ID 與標籤 ID")
    @ApiResponse(responseCode = "200", description = "商品建立成功",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        log.info("建立新商品：{}", request.getName());
        return ResponseEntity.ok(productService.create(request));
    }

    @Operation(summary = "更新商品（限管理員）", description = "根據 ID 更新商品資料，需附上分類 ID、圖片 ID 與標籤 ID")
    @ApiResponse(responseCode = "200", description = "更新成功",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        log.info("更新商品 ID = {}，內容 = {}", id, request);
        return ResponseEntity.ok(productService.update(id, request));
    }

    @Operation(summary = "刪除商品（限管理員）", description = "根據 ID 刪除指定商品")
    @ApiResponse(responseCode = "204", description = "刪除成功")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("刪除商品 ID = {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
