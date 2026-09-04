package com.homewerk.backend.config.admin;

import com.homewerk.backend.user.repository.UserRepository;
import com.homewerk.backend.user.model.User;
import com.homewerk.backend.user.enums.UserRole;
import com.homewerk.backend.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {


    private final BootstrapAdminProperties bootstrapAdminProperties;

    @Bean
    public CommandLineRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            boolean hasAdmin =
                    userRepository.existsByRole(UserRole.ADMIN);

            String email =
                    bootstrapAdminProperties.getEmail();

            String password =
                    bootstrapAdminProperties.getPassword();

            boolean missingEnvValues =
                    email == null || email.isBlank()
                            || password == null || password.isBlank();

            if (hasAdmin) {
                log.info("Bootstrap admin already exists; skipping admin seed.");
                return;
            }

            if (missingEnvValues) {
                log.warn(
                        "No admin exists, but bootstrap admin credentials are not configured."
                );
                return;
            }

            User admin = new User();

            admin.setDisplayName("Admin");
            admin.setEmail(email);
            admin.setPassword(
                    passwordEncoder.encode(password)
            );
            admin.setRole(UserRole.ADMIN);
            admin.setStatus(UserStatus.ACTIVE);
            admin.setEmailVerified(true);

            userRepository.save(admin);

            log.info("Bootstrap admin created successfully.");
        };
    }
}