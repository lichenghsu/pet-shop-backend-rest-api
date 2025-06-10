package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.ReviewRequest;
import com.lichenghsu.petshop.dto.ReviewResponse;
import com.lichenghsu.petshop.entity.Product;
import com.lichenghsu.petshop.entity.Review;
import com.lichenghsu.petshop.repository.ProductRepository;
import com.lichenghsu.petshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        Review review = new Review();
        review.setProduct(product);
        review.setUsername(request.getUsername());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);
        log.info("使用者 [{}] 評論商品 [{}]：{}", review.getUsername(), product.getId(), review.getComment());

        return mapToResponse(saved);
    }

    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(Review review) {
        ReviewResponse res = new ReviewResponse();
        res.setId(review.getId());
        res.setUsername(review.getUsername());
        res.setRating(review.getRating());
        res.setComment(review.getComment());
        res.setCreatedAt(review.getCreatedAt());
        return res;
    }
}
