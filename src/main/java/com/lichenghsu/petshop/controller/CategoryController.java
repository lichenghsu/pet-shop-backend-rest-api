package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.CategoryRequest;
import com.lichenghsu.petshop.dto.CategoryResponse;
import com.lichenghsu.petshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "分類管理", description = "提供分類查詢、新增、刪除功能")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "取得所有分類")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "依 ID 查詢分類")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "依名稱查詢分類")
    public ResponseEntity<CategoryResponse> getByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "新增分類（限管理員）")
    @Transactional
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "刪除分類（限管理員）")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
