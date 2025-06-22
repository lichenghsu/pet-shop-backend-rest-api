package com.lichenghsu.petshop.scheduler;

import com.lichenghsu.petshop.entity.Image;
import com.lichenghsu.petshop.repository.ImageRepository;
import com.lichenghsu.petshop.repository.ProductImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageCleanupScheduler {

    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;

    // 每天凌晨 3 點執行
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void cleanUpUnusedImages() {
        log.info("啟動未使用圖片清理排程...");

        List<Image> allImages = imageRepository.findAll();

        int deletedCount = 0;

        for (Image image : allImages) {
            boolean isUsed = productImageRepository.existsByImageId(image.getId());
            if (!isUsed) {
                imageRepository.delete(image);
                deletedCount++;
            }
        }

        log.info("未使用圖片清理完成，共刪除 {} 張圖片", deletedCount);
    }
}

