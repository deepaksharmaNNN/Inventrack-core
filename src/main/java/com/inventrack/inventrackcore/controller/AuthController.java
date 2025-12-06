package com.inventrack.inventrackcore.controller;

import com.inventrack.inventrackcore.dto.AuthRequest;
import com.inventrack.inventrackcore.dto.AuthResponse;
import com.inventrack.inventrackcore.dto.UserRegisterDto;
import com.inventrack.inventrackcore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService userService;

    public AuthController(UserService s) { this.userService = s; }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody UserRegisterDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(userService.authenticate(req));
    }
}
