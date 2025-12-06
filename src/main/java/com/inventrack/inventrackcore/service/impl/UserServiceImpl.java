package com.inventrack.inventrackcore.service.impl;

import com.inventrack.inventrackcore.dto.AuthRequest;
import com.inventrack.inventrackcore.dto.AuthResponse;
import com.inventrack.inventrackcore.dto.UserRegisterDto;
import com.inventrack.inventrackcore.entity.Role;
import com.inventrack.inventrackcore.entity.User;
import com.inventrack.inventrackcore.repository.RoleRepository;
import com.inventrack.inventrackcore.repository.UserRepository;
import com.inventrack.inventrackcore.security.JwtUtil;
import com.inventrack.inventrackcore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Long register(UserRegisterDto dto) {
        if (userRepo.existsByUsername(dto.getUsername()) || userRepo.existsByEmail(dto.getEmail()))
            throw new RuntimeException("User exists");

        Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_USER").build()));

        User u = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Collections.singleton(userRole))
                .enabled(true)
                .build();

        User saved = userRepo.save(u);
        return saved.getId();
    }

    @Override
    public AuthResponse authenticate(AuthRequest req) {
        User u = userRepo.findByUsername(req.getUsernameOrEmail())
                .orElseGet(() -> userRepo.findByEmail(req.getUsernameOrEmail()).orElse(null));
        if (u == null) throw new RuntimeException("Invalid credentials");

        if (!passwordEncoder.matches(req.getPassword(), u.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = jwtUtil.generateToken(u.getUsername(), u.getId());
        return AuthResponse.builder().accessToken(token).userId(u.getId()).username(u.getUsername()).build();
    }
}