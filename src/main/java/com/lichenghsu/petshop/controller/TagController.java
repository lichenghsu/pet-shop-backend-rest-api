package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.TagRequest;
import com.lichenghsu.petshop.dto.TagResponse;
import com.lichenghsu.petshop.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "標籤管理", description = "提供標籤的查詢與新增刪除功能")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "取得所有標籤")
    public ResponseEntity<List<TagResponse>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "依 ID 取得標籤")
    public ResponseEntity<TagResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "依名稱查詢標籤")
    public ResponseEntity<TagResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(tagService.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "新增標籤（限管理員）")
    public ResponseEntity<TagResponse> create(@RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "刪除標籤（限管理員）")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
