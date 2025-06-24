package com.lichenghsu.petshop.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用戶實體，包含帳號、密碼、信箱與角色")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "用戶唯一識別碼")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "用戶名稱，必須唯一")
    private String username;

    @Column(nullable = false)
    @Schema(description = "加密後的密碼")
    private String password;

    @Schema(description = "用戶電子郵件")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Schema(description = "用戶所擁有的角色清單")
    private Set<Role> roles;

    @Column(nullable = false)
    @Schema(description = "帳號是否啟用")
    private boolean enabled = true;

    @Column(nullable = false, updatable = false)
    @Schema(description = "註冊時間")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}