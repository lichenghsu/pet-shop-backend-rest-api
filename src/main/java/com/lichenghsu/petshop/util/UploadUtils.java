package com.lichenghsu.petshop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadUtils {
    public static String saveImage(MultipartFile file, String uploadDir) throws IOException {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        File dest = new File(uploadDir, filename);
        file.transferTo(dest);
        return "/uploads/" + filename; // 回傳可被前端訪問的 URL path
    }
}