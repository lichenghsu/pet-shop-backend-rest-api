package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.ImageResponse;
import com.lichenghsu.petshop.entity.Image;
import com.lichenghsu.petshop.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public ImageResponse upload(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            byte[] imageBytes = Files.readAllBytes(filePath);

            Image image = new Image();
            image.setFilename(filename);
            image.setData(imageBytes);
            image.setContentType(file.getContentType());

            imageRepository.save(image);
            Files.deleteIfExists(filePath);

            return new ImageResponse(image.getId(), "/api/images/" + image.getId());
        } catch (IOException e) {
            throw new RuntimeException("圖片上傳失敗", e);
        }
    }

    @Transactional
    public Long saveImage(InputStream inputStream) {
        try {
            byte[] imageBytes = inputStream.readAllBytes();

            Image image = new Image();
            image.setFilename(UUID.randomUUID() + ".jpg");
            image.setData(imageBytes);
            image.setContentType("image/jpeg");

            imageRepository.save(image);
            return image.getId();

        } catch (IOException e) {
            throw new RuntimeException("圖片儲存失敗", e);
        }
    }
}