package com.example.demo.Config;

import com.example.demo.Repasitory.UserRepository;
import com.example.demo.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = new User(
                    "a",
                    "p",
                    "a",
                    "09",
                    "n",
                    true,
                    true
            );
            User user2 = new User(
                    "a2",
                    "p2",
                    "a2",
                    "092",
                    "n2",
                    false,
                    true
            );
            userRepository.saveAll(List.of(user, user2));
        };
    }
}
