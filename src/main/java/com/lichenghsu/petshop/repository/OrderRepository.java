package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
