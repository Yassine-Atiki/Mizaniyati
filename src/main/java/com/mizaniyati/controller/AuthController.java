package com.mizaniyati.controller;

import com.mizaniyati.dto.AuthResponseDTO;
import com.mizaniyati.dto.LoginRequestDTO;
import com.mizaniyati.dto.RegisterRequestDTO;
import com.mizaniyati.dto.UserResponseDTO;
import com.mizaniyati.entity.User;
import com.mizaniyati.mapper.UserMapper;
import com.mizaniyati.service.AuthService;
import com.mizaniyati.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // LE BON IMPORT
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        // authentication.getName() renvoie l'identifiant (email) stocké dans le JWT
        String email = authentication.getName();

        // On récupère l'utilisateur en base
        User user = userService.findByEmail(email);

        // On convertit en DTO pour ne pas exposer le mot de passe
        return ResponseEntity.ok(userMapper.toResponseDto(user));
    }
}