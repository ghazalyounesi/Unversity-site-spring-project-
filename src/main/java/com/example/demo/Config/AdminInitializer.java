package com.example.demo.Config;

import com.example.demo.Repasitory.UserRepository;
import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        userRepository.findByUsername(adminUsername).ifPresentOrElse(admin -> log.info("admin is already registered {}", admin), () -> {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setName("Admin");
            adminUser.setPhone("0000000000");
            adminUser.setNationalld("0000000000");
            adminUser.setRoles(Set.of("ADMIN"));
            adminUser.setActive(true);

            userRepository.save(adminUser);
            log.info(">>>> Admin user created successfully!");
        });
    }

}