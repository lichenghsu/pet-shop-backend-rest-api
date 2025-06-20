package com.lichenghsu.petshop.controller;

import com.lichenghsu.petshop.dto.ImageResponse;
import com.lichenghsu.petshop.entity.Image;
import com.lichenghsu.petshop.repository.ImageRepository;
import com.lichenghsu.petshop.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "圖片", description = "用於上傳和搜尋圖片的 API")
public class ImageController {

    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final RedisTemplate<String, byte[]> redisTemplate;

    @GetMapping("/{id}")
    @Operation(summary = "透過 ID 取得圖片", description = "通過圖片 ID 取得圖片，支持 REDIS CACHE。")
    @ApiResponse(responseCode = "200", description = "圖片搜尋成功", content = @Content(mediaType = "image/jpeg"))
    @ApiResponse(responseCode = "404", description = "未找到圖片")
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "Image ID", required = true) @PathVariable Long id) {

        String redisKey = "image:" + id;
        byte[] imageData = redisTemplate.opsForValue().get(redisKey);

        if (imageData == null || imageData.length == 0) {
            log.info("Redis miss or empty, fetching image from DB: {}", id);
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));

            imageData = image.getData();
            log.info("Fetched image from DB, length = {}", (imageData != null ? imageData.length : 0));

            if (imageData != null && imageData.length > 0) {
                redisTemplate.opsForValue().set(redisKey, imageData, 5, TimeUnit.MINUTES);
                log.info("Image cached to Redis for 5 minutes: {}", redisKey);
            } else {
                log.warn("Image data is empty, not caching to Redis");
            }
        } else {
            log.info("Redis hit: {}", redisKey);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "上傳圖片",
            description = "管理員可上傳一張或多張圖 (Swagger UI 無法上傳，請用 Postman 測試）"
    )
    @ApiResponse(responseCode = "200", description = "圖片成功上傳",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ImageResponse.class))))
    public ResponseEntity<?> upload(
            @Parameter(description = "圖片檔案陣列", required = true)
            @RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            log.warn("未收到任何檔案");
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "未收到任何檔案"));
        }

        List<ImageResponse> uploaded = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                log.info("開始上傳圖片: {}", file.getOriginalFilename());
                ImageResponse response = imageService.upload(file);
                uploaded.add(response);
            } catch (Exception e) {
                log.error("圖片上傳失敗: {}，原因: {}", file.getOriginalFilename(), e.getMessage());
                failedFiles.add(file.getOriginalFilename());
            }
        }

        if (!failedFiles.isEmpty()) {
            String msg = "部分圖片上傳失敗: " + String.join(", ", failedFiles);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", msg));
        }
        log.info("圖片新增成功");
        return ResponseEntity.ok(uploaded);
    }

}
