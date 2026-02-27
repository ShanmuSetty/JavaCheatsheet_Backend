package com.example.dsa_backend.service;

import com.example.dsa_backend.JwtUtil;
import com.example.dsa_backend.User;
import com.example.dsa_backend.UserRepository;
import com.example.dsa_backend.dto.AuthRequest;
import com.example.dsa_backend.dto.AuthResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Returns token + email so the frontend can log the user in immediately after registering
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        String hashed = passwordEncoder.encode(request.getPassword());
        userRepository.save(new User(request.getEmail(), hashed));

        // Auto-login: generate token right away
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token, request.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }
}