package com.example.demo.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.exception.UserNotFoundCheckedException;
import com.example.demo.model.dto.Login.LoginRequest;
import com.example.demo.model.dto.Login.LoginResponse;
import com.example.demo.model.dto.UserRegistrationRequest;
import com.example.demo.model.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registerUser(UserRegistrationRequest request) throws EntityAlreadyExistsException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException("Username already taken: " + request.getUsername());
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new EntityAlreadyExistsException("Phone number already taken: " + request.getPhone());
        }

        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .phone(request.getPhone())
                .nationalld(request.getNationalId())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(new HashSet<>())
                .build();


    }

    public LoginResponse loginUser(LoginRequest request) throws UserNotFoundCheckedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundCheckedException("User not found"));
        String jwtToken = jwtService.generateToken(user);

        return new LoginResponse(jwtToken, user.getUsername());
    }
}