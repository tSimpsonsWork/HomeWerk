package com.homewerk.backend.user.service;

import com.homewerk.backend.user.dto.LoginRequest;
import com.homewerk.backend.user.dto.LoginResponse;
import com.homewerk.backend.user.dto.SignupRequest;
import com.homewerk.backend.user.dto.SignupResponse;
import com.homewerk.backend.user.enums.UserRole;
import com.homewerk.backend.user.enums.UserStatus;
import com.homewerk.backend.user.model.User;
import com.homewerk.backend.user.repository.UserRepository;
import com.homewerk.backend.utils.EmailValidationUtil;
import com.homewerk.backend.utils.InputValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public SignupResponse signup(SignupRequest request) {

        String normalizedEmail =
                EmailValidationUtil.normalize(request.email());

        EmailValidationUtil.validate(normalizedEmail);

        String normalizedDisplayName =
                InputValidationUtil.normalizeName(
                        request.displayName(),
                        "Display name"
                );

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            log.warn("USER_SIGNUP_FAILED reason=EMAIL_ALREADY_EXISTS");
            throw new IllegalArgumentException(
                    "An account with this email already exists"
            );
        }

        User user = new User();

        user.setEmail(normalizedEmail);
        user.setDisplayName(normalizedDisplayName);

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

    public LoginResult login(LoginRequest request) {

        String normalizedEmail =
                EmailValidationUtil.normalize(request.email());

        EmailValidationUtil.validate(normalizedEmail);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                normalizedEmail,
                                request.password()
                        )
                );

        User user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password")
                );

        log.info("USER_LOGIN_SUCCESS");

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole()
        );

        return new LoginResult(
                authentication,
                response
        );
    }

    public LoginResponse getCurrentUser(String email) {

        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Authenticated user not found")
                );

        return new LoginResponse(
                user.getId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole()
        );
    }

}