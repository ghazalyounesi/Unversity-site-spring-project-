// src/main/java/com/example/demo/AdminInitializer.java
package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.Repasitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import java.util.Set;

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
        if (!userRepository.findByUsername(adminUsername).isPresent()) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setName("Admin");
            adminUser.setPhone("0000000000");
            adminUser.setNationalld("0000000000");
            adminUser.setRoles(Set.of("ADMIN"));
            adminUser.setActive(true);

            userRepository.save(adminUser);
            System.out.println(">>>> Admin user created successfully!");
        }
    }
}