package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
