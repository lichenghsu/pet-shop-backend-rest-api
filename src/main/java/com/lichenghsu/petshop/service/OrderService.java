package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.*;
import com.lichenghsu.petshop.entity.*;
import com.lichenghsu.petshop.enums.OrderStatus;
import com.lichenghsu.petshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice() * itemReq.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        orderRepository.save(order);

        return mapToResponse(order);
    }

    public List<OrderResponse> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse getOrderById(Long orderId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return mapToResponse(order);
    }

    @Transactional
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderItems(Long orderId, List<OrderItemRequest> newItems) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // remove existing items
        orderItemRepository.deleteAll(order.getItems());

        // rebuild new items
        List<OrderItem> updatedItems = newItems.stream().map(itemReq -> {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice() * itemReq.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(updatedItems);
        return mapToResponse(order);
    }

    @Transactional
    public AdminOrderResponse getAdminOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return AdminOrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .email(order.getUser().getEmail())
                .items(order.getItems().stream().map(item ->
                        AdminOrderResponse.OrderItemDto.builder()
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Only PENDING or PAID orders can be cancelled.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return mapToResponse(order);
    }

    @Transactional
    public List<OrderResponse> queryOrders(OrderQueryParams params) {
        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> {
                    boolean match = true;

                    if (params.getUsername() != null && !params.getUsername().isEmpty()) {
                        match &= order.getUser().getUsername().equals(params.getUsername());
                    }

                    if (params.getStatus() != null) {
                        match &= order.getStatus() == params.getStatus();
                    }

                    if (params.getStartDate() != null) {
                        match &= !order.getCreatedAt().toLocalDate().isBefore(params.getStartDate());
                    }

                    if (params.getEndDate() != null) {
                        match &= !order.getCreatedAt().toLocalDate().isAfter(params.getEndDate());
                    }

                    return match;
                })
                .toList();

        return orders.stream().map(this::mapToResponse).toList();
    }

    public AdminOrderResponse getAdminOrderDetails(Long id) {
        return getAdminOrderById(id);
    }

    @Transactional
    public void batchUpdateStatuses(List<Long> orderIds, OrderStatus status) {
        List<Order> orders = orderRepository.findAllById(orderIds);

        for (Order order : orders) {
            order.setStatus(status);
        }

        // 如果你使用 JPA 且啟用 Transactional，修改後會自動 flush。
        // 若要強制立即儲存到 DB，可呼叫 saveAll（但通常不需要）
        // orderRepository.saveAll(orders);
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setStatus(OrderStatus.valueOf(order.getStatus().name()));
        dto.setCreatedAt(order.getCreatedAt());

        dto.setUserId(order.getUser().getId());
        dto.setUsername(order.getUser().getUsername());
        dto.setEmail(order.getUser().getEmail());

        dto.setItems(
                order.getItems().stream()
                        .map(item -> {
                            OrderResponse.OrderItemDto itemDto = new OrderResponse.OrderItemDto();
                            itemDto.setProductName(item.getProduct().getName());
                            itemDto.setQuantity(item.getQuantity());
                            itemDto.setPrice(item.getPrice());
                            return itemDto;
                        })
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
