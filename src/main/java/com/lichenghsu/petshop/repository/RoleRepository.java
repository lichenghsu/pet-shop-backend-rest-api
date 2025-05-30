package com.lichenghsu.petshop.repository;

import com.lichenghsu.petshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}

