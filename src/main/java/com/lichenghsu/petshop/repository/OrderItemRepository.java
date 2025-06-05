package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
