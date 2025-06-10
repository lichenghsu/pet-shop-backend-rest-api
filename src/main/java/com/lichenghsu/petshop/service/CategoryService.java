package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.CategoryRequest;
import com.lichenghsu.petshop.dto.CategoryResponse;
import com.lichenghsu.petshop.entity.Category;
import com.lichenghsu.petshop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        log.info("查詢所有分類");
        return categoryRepository.findAll().stream()
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        log.info("依 ID 查詢分類，ID = {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryResponse(category.getId(), category.getName());
    }

    public CategoryResponse findByName(String name) {
        log.info("依名稱查詢分類，名稱 = {}", name);
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryResponse(category.getId(), category.getName());
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        log.info("嘗試建立分類：{}", request.getName());

        if (categoryRepository.existsByName(request.getName())) {
            log.warn("分類已存在：{}", request.getName());
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        Category saved = categoryRepository.save(category);

        log.info("分類建立成功：{}", saved.getName());
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    @Transactional
    public void delete(Long id) {
        log.info("刪除分類，ID = {}", id);
        categoryRepository.deleteById(id);
    }
}
