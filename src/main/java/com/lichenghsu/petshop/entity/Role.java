package com.lichenghsu.petshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String name; // 例如 ROLE_USER、ROLE_ADMIN
}

