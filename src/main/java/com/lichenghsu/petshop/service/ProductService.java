package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.ProductRequest;
import com.lichenghsu.petshop.dto.ProductResponse;
import com.lichenghsu.petshop.entity.Product;
import com.lichenghsu.petshop.entity.ProductImage;
import com.lichenghsu.petshop.entity.Image;
import com.lichenghsu.petshop.repository.ProductRepository;
import com.lichenghsu.petshop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

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

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // 🟡 Null-safe: 支援沒有圖片時仍可建立商品
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
                    }).collect(Collectors.toList());
        }

        product.setImages(productImages);
        return mapToResponse(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrls(product.getImages().stream()
                .map(pi -> pi.getImage().getUrl())
                .collect(Collectors.toList()));
        return dto;
    }
}
