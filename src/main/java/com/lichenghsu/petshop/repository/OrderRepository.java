package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Order;
import com.lichenghsu.petshop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT SUM(i.price) FROM OrderItem i")
    Double sumAllOrderAmount();

    @Query("""
     SELECT FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m') AS month,
            SUM(i.price) AS total
     FROM OrderItem i
     JOIN i.order o
     GROUP BY month
     ORDER BY month ASC
""")
    List<Object[]> sumMonthlyRevenue();
}
