package com.lichenghsu.petshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String contentType;
    private String productCode;

    @Lob
    @Column(length = 5_000_000)
    private byte[] data;  // 存圖片二進位內容

    @Transient
    public String getUrl() {
        return "/api/images/" + id; // 或依照你的邏輯組出網址
    }
}
