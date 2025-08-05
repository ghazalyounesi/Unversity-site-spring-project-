// src/main/java/com/example/demo/service/AuthService.java
package com.example.demo.Service;

import com.example.demo.dto.Login.LoginRequest;
import com.example.demo.dto.Login.LoginResponse;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.entity.User;
import com.example.demo.Service.JwtService;
import com.example.demo.Repasitory.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new EntityExistsException("Username already taken: " + request.getUsername());
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new EntityExistsException("Phone number already taken: " + request.getPhone());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setNationalld(request.getNationalId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setRoles(new HashSet<>());

        return userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);

        return new LoginResponse(jwtToken, user.getUsername());
    }
}