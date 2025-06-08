package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Order;
import com.lichenghsu.petshop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);
}
