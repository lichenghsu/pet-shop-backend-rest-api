package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.ImageResponse;
import com.lichenghsu.petshop.entity.Image;
import com.lichenghsu.petshop.repository.ImageRepository;
import com.lichenghsu.petshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final RedisTemplate<String, byte[]> redisTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        String redisKey = "image:" + id;
        byte[] imageData = redisTemplate.opsForValue().get(redisKey);

        if (imageData == null || imageData.length == 0) {
            System.out.println("Redis miss or empty, fetching image from DB: " + id);
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));

            imageData = image.getData();
            System.out.println("Fetched image from DB, length = " + (imageData != null ? imageData.length : 0));

            if (imageData != null && imageData.length > 0) {
                redisTemplate.opsForValue().set(redisKey, imageData, 5, TimeUnit.MINUTES);
            } else {
                System.out.println("Warning: image data is empty, not caching to Redis");
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public List<ImageResponse> upload(@RequestParam("files") MultipartFile[] files) {

        return Arrays.stream(files)
                .map(imageService::upload)
                .toList();
    }

}
