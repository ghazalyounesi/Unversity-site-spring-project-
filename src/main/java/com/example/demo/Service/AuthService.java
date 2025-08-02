// src/main/java/com/example/demo/service/AuthService.java
package com.example.demo.Service;

import com.example.demo.dto.Login.LoginRequest;
import com.example.demo.dto.Login.LoginResponse;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.entity.User;
import com.example.demo.Repasitory.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setActive(false);
        user.setRoles(new HashSet<>());

        return userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new IllegalStateException("User account is not active.");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = "dummy-jwt-token-for-" + user.getUsername();
            return new LoginResponse(token, user.getUsername());
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}