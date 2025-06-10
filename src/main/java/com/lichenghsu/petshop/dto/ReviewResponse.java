package com.lichenghsu.petshop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {

    private Long id;
    private String username;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
