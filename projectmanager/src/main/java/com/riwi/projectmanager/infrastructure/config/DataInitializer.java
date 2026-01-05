package com.riwi.projectmanager.infrastructure.config;

import com.riwi.projectmanager.infrastructure.security.entity.UserEntity;
import com.riwi.projectmanager.infrastructure.security.repository.JpaUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(JpaUserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (!userRepo.existsByEmail("admin@admin.com")) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setEmail("admin@admin.com");
                admin.setPassword(encoder.encode("admin123"));
                userRepo.save(admin);
            }
        };
    }
}
