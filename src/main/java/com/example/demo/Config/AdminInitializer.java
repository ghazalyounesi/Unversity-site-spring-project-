package com.example.demo.Config;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @PostConstruct
    @Transactional
    public void initAdmin() {
        userRepository.findByUsername(adminUsername).ifPresentOrElse(
                admin -> log.info("admin is already registered {}", admin),
                () -> {
                    User adminUser = User.builder()
                            .username(adminUsername)
                            .password(passwordEncoder.encode(adminPassword))
                            .name("Admin")
                            .phone("0000000000")
                            .nationalld("0000000000")
                            .roles(Set.of("ADMIN"))
                            .active(true)
                            .build();

                    userRepository.save(adminUser);
                    log.info(">>>> Admin user created successfully!");
                }
        );
    }

}
