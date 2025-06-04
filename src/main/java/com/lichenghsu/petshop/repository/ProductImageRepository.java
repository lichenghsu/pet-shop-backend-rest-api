package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
