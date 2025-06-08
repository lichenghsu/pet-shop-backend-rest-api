package com.lichenghsu.petshop.service;

import com.lichenghsu.petshop.dto.AuthRequest;
import com.lichenghsu.petshop.dto.AuthResponse;
import com.lichenghsu.petshop.entity.Role;
import com.lichenghsu.petshop.entity.User;
import com.lichenghsu.petshop.repository.RoleRepository;
import com.lichenghsu.petshop.repository.UserRepository;
import com.lichenghsu.petshop.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );
        authManager.authenticate(authentication);

        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtProvider.generateToken(user);

        return new AuthResponse(token);
    }

    public void register(AuthRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(roleRepo.findById("ROLE_USER").orElse(new Role("ROLE_USER"))))
                .build();

        userRepo.save(user);
    }
}
