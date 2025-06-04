package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}