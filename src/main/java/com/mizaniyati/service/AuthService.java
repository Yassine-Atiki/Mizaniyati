package com.mizaniyati.service;

import com.mizaniyati.dto.AuthResponseDTO;
import com.mizaniyati.dto.LoginRequestDTO;
import com.mizaniyati.dto.RegisterRequestDTO;
import com.mizaniyati.entity.User;
import com.mizaniyati.enums.Currency;
import com.mizaniyati.repository.UserRepository;
import com.mizaniyati.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default currency as user DTO doesn't have it
        user.setCurrency(Currency.MAD);

        userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }
}
