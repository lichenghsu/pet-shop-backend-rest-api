package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> findByName(String name);
}
