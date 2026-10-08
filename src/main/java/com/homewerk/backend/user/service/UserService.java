package com.homewerk.backend.user.service;

import com.homewerk.backend.config.admin.AdminSecurityProperties;
import com.homewerk.backend.user.dto.*;
import com.homewerk.backend.user.enums.PasswordResetType;
import com.homewerk.backend.user.enums.UserRole;
import com.homewerk.backend.user.enums.UserStatus;
import com.homewerk.backend.user.exception.EmailAlreadyExistsException;
import com.homewerk.backend.user.exception.InvalidCredentialsException;
import com.homewerk.backend.user.exception.InvalidPasswordRecoveryTokenException;
import com.homewerk.backend.user.model.PasswordResetToken;
import com.homewerk.backend.user.model.User;
import com.homewerk.backend.user.repository.PasswordResetTokenRepository;
import com.homewerk.backend.user.repository.UserRepository;
import com.homewerk.backend.utils.EmailValidationUtil;
import com.homewerk.backend.utils.InputValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetRateLimitService passwordResetRateLimitService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AdminSecurityProperties adminSecurityProperties;

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
            throw new EmailAlreadyExistsException();
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

        Authentication authentication;

        try {
            authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    normalizedEmail,
                                    request.password()
                            )
                    );
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

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

    //sends token
    @Transactional
    public void forgotPassword(String email, String ipAddress) {

        String normalizedEmail =
                EmailValidationUtil.normalize(email);

        boolean allowed =
                passwordResetRateLimitService.isAllowed(
                        normalizedEmail,
                        ipAddress
                );

        passwordResetRateLimitService.recordAttempt(
                normalizedEmail,
                ipAddress
        );

        if (!allowed) {
            return;
        }

        Optional<User> optionalUser =
                userRepository.findByEmailIgnoreCase(
                        normalizedEmail
                );

        if (optionalUser.isEmpty()) {
            return;
        }

        User user = optionalUser.get();
        if (user.getRole() == UserRole.ADMIN) {
            log.warn("PASSWORD_RECOVERY_BLOCKED role=ADMIN");
            return;
        }

        String rawToken =
                passwordResetTokenService.generateRawToken();

        log.debug("DEV PASSWORD RECOVERY TOKEN={}", rawToken);

        String tokenHash =
                passwordResetTokenService.hashToken(rawToken);

        Instant now = Instant.now();

        PasswordResetToken resetToken =
                new PasswordResetToken();

        resetToken.setUser(user);
        resetToken.setTokenHash(tokenHash);
        resetToken.setResetType(
                PasswordResetType.FORGOT_PASSWORD
        );
        resetToken.setCreatedAt(now);
        resetToken.setExpiresAt(
                now.plus(Duration.ofMinutes(30))
        );
        resetToken.setUsed(false);

        passwordResetTokenRepository.deleteByUser(user);
        passwordResetTokenRepository.save(resetToken);

        // rawToken is still here in memory.
        // Later we'll pass THIS to the email service.
    }

    //uses token from forgetPassword
    @Transactional
    public void recoverPassword(PasswordRecoveryRequest request) {

        String tokenHash =
                passwordResetTokenService.hashToken(
                        request.token()
                );

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByTokenHash(tokenHash)
                        .orElseThrow(
                                InvalidPasswordRecoveryTokenException::new
                        );

        if (resetToken.isUsed()) {
            throw new InvalidPasswordRecoveryTokenException();
        }

        if (resetToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidPasswordRecoveryTokenException();
        }

        if (resetToken.getResetType()
                != PasswordResetType.FORGOT_PASSWORD) {
            throw new InvalidPasswordRecoveryTokenException();
        }

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );

        resetToken.setUsed(true);

        userRepository.save(user);
        passwordResetTokenRepository.save(resetToken);
    }

    @Transactional
    public void changePassword(
            String authenticatedEmail,
            ChangePasswordRequest request
    ) {
        User user = userRepository
                .findByEmailIgnoreCase(authenticatedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        boolean currentPasswordMatches =
                passwordEncoder.matches(
                        request.currentPassword(),
                        user.getPassword()
                );

        if (!currentPasswordMatches) {
            throw new InvalidCredentialsException();
        }

        if (user.getRole() == UserRole.ADMIN) {

            if (request.adminPin() == null) {
                throw new InvalidCredentialsException();
            }

            if (!request.adminPin().equals(
                    adminSecurityProperties.getAdminPin()
            )) {
                throw new InvalidCredentialsException();
            }
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );

        userRepository.save(user);
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