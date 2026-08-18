package com.edusync.service;

import com.edusync.dto.AuthDto;
import com.edusync.model.User;
import com.edusync.repository.UserRepository;
import com.edusync.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication service.
 * Handles register + login → returns JWT token.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final AuditService auditService;

    // ─── Register ─────────────────────────────────────────────────────────

    public AuthDto.AuthResponse register(AuthDto.RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered: " + req.getEmail());
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole() != null ? req.getRole() : User.Role.STUDENT)
                .department(req.getDepartment())
                .semester(req.getSemester())
                .section(req.getSection())
                .rollNumber(req.getRollNumber())
                .employeeId(req.getEmployeeId())
                .phone(req.getPhone())
                .build();

        User saved = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getEmail());
        String token = jwtUtil.generateToken(userDetails, saved.getId(), saved.getRole().name());

        auditService.log(
                "USER_REGISTERED",
                saved.getEmail(),
                saved.getName(),
                saved.getRole(),
                "User",
                saved.getId().toString(),
                "User registered with role " + saved.getRole()
        );

        return AuthDto.AuthResponse.builder()
                .token(token)
                .userId(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .department(saved.getDepartment())
                .semester(saved.getSemester())
                .message("Registration successful! Welcome to EduSync.")
                .build();
    }

    // ─── Login ────────────────────────────────────────────────────────────

    public AuthDto.AuthResponse login(AuthDto.LoginRequest req) {
        // Spring Security validates credentials
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("Account is deactivated. Contact admin.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails, user.getId(), user.getRole().name());

        auditService.log(
                "USER_LOGIN",
                user.getEmail(),
                user.getName(),
                user.getRole(),
                "User",
                user.getId().toString(),
                "Successful login from web app"
        );

        return AuthDto.AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .department(user.getDepartment())
                .semester(user.getSemester())
                .message("Login successful!")
                .build();
    }
}
