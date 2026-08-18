package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.dto.AuthDto;
import com.edusync.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller.
 *
 * POST /api/auth/register  — create account
 * POST /api/auth/login     — get JWT token
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AppDto.ApiResponse<AuthDto.AuthResponse>> register(
            @Valid @RequestBody AuthDto.RegisterRequest request
    ) {
        AuthDto.AuthResponse response = authService.register(request);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<AppDto.ApiResponse<AuthDto.AuthResponse>> login(
            @Valid @RequestBody AuthDto.LoginRequest request
    ) {
        AuthDto.AuthResponse response = authService.login(request);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Login successful", response));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("EduSync API is running ✅");
    }
}
