package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.DashboardResponse;
import com.lichenghsu.petshop.dto.MonthlyRevenueItem;
import com.lichenghsu.petshop.repository.OrderRepository;
import com.lichenghsu.petshop.repository.ProductRepository;
import com.lichenghsu.petshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public DashboardResponse getDashboardData() {
        DashboardResponse dto = new DashboardResponse();
        dto.setProductCount(productRepository.count());
        dto.setUserCount(userRepository.count());
        dto.setTotalOrderAmount(orderRepository.sumAllOrderAmount());

        List<Object[]> rawList = orderRepository.sumMonthlyRevenue();

        List<MonthlyRevenueItem> list = rawList.stream()
                .map(row -> {
                    MonthlyRevenueItem item = new MonthlyRevenueItem();
                    item.setMonth((String) row[0]);
                    item.setTotal(((Number) row[1]).doubleValue());
                    return item;
                })
                .collect(Collectors.toList());

        dto.setMonthlyRevenue(list);

        return dto;
    }
}