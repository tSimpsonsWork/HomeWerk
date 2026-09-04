package com.homewerk.backend.user.service;

import com.homewerk.backend.user.dto.SignupRequest;
import com.homewerk.backend.user.dto.SignupResponse;
import com.homewerk.backend.user.enums.UserRole;
import com.homewerk.backend.user.enums.UserStatus;
import com.homewerk.backend.user.model.User;
import com.homewerk.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {

        String normalizedEmail =
                request.email()
                        .trim()
                        .toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {

            log.warn("USER_SIGNUP_FAILED reason=EMAIL_ALREADY_EXISTS");

            throw new IllegalArgumentException(
                    "An account with this email already exists"
            );
        }

        User user = new User();

        user.setEmail(normalizedEmail);
        user.setDisplayName(request.displayName().trim());

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.PENDING);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        log.info("USER_SIGNUP_SUCCESS");

        return new SignupResponse(
                savedUser.getEmail(),
                savedUser.getDisplayName(),
                savedUser.getStatus()
        );
    }
}