package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.ProductRequest;
import com.lichenghsu.petshop.dto.ProductResponse;
import com.lichenghsu.petshop.entity.*;
import com.lichenghsu.petshop.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "product", key = "#id")
    public ProductResponse findById(Long id) {
        return mapToResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // 分類
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
            product.setCategory(category);
        }

        // 標籤
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            product.setTags(new HashSet<>(tags));
        } else {
            product.setTags(Collections.emptySet()); // 防止 nullPointer
        }

        // 圖片關聯
        List<ProductImage> productImages = new ArrayList<>();
        if (request.getImageIds() != null && !request.getImageIds().isEmpty()) {
            productImages = request.getImageIds().stream()
                    .map(imageId -> {
                        Image image = imageRepository.findById(imageId)
                                .orElseThrow(() -> new RuntimeException("Image not found: " + imageId));
                        ProductImage pi = new ProductImage();
                        pi.setImage(image);
                        pi.setProduct(product);
                        return pi;
                    })
                    .collect(Collectors.toList());
        }
        product.setImages(productImages); // 即使為空也應設定，否則預設為 null

        return mapToResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // 分類
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
            product.setCategory(category);
        }

        // 標籤
        if (request.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            product.setTags(new HashSet<>(tags));
        } else {
            product.setTags(Collections.emptySet());
        }

        // 圖片
        if (request.getImageIds() != null) {
            List<Long> incomingIds = request.getImageIds();

            productImageRepository.deleteByProductId(product.getId());

            List<ProductImage> newProductImages = incomingIds.stream()
                    .map(imageId -> {
                        Image image = imageRepository.findById(imageId)
                                .orElseThrow(() -> new RuntimeException("Image not found: " + imageId));
                        ProductImage pi = new ProductImage();
                        pi.setImage(image);
                        pi.setProduct(product);
                        return pi;
                    })
                    .toList();

            // 用 setImages 覆蓋整個 List，避免 Hibernate session cache 衝突
            product.getImages().clear();
            product.getImages().addAll(newProductImages);
        }

        Hibernate.initialize(product.getImages());
        Hibernate.initialize(product.getTags());

        return mapToResponse(productRepository.save(product));
    }


    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {

        List<Long> imageIds = product.getImages().stream()
                .map(pi -> pi.getImage().getId())
                .collect(Collectors.toList());

        List<String> imageUrls = product.getImages().stream()
                .map(pi -> pi.getImage().getUrl())
                .toList();

        List<String> tagNames = product.getTags().stream()
                .map(Tag::getName)
                .toList();

        List<Long> tagIds = product.getTags().stream()
                .map(Tag::getId)
                .toList();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                imageIds,
                imageUrls,
                product.getCategory().getId(),
                product.getCategory().getName(),
                tagIds,
                tagNames
        );
    }


}
