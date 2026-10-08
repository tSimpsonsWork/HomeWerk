package com.homewerk.backend.controller.user;

import com.homewerk.backend.user.dto.*;
import com.homewerk.backend.user.service.LoginResult;
import com.homewerk.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    //POST http://localhost:8080/auth/signup{"email": "@gmail.com","displayName": "Test User","password": "stuff"}
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return userService.signup(request);
    }

    //POST http://localhost:8081/auth/login{"email": "@gmail.com","password": "stuff"}
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        LoginResult result = userService.login(request);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(result.authentication());

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                httpRequest,
                httpResponse
        );

        return result.response();
    }

    //POST  http://localhost:8081/auth/forgot-password// {"email": "@gmail.com"}
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ForgotPasswordResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request, HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddr();

        userService.forgotPassword(
                request.email(),
                ipAddress
        );

        return new ForgotPasswordResponse(
                "If an account exists for this email, password reset instructions will be sent."
        );
    }

    //POST  http://localhost:8081/auth/recover-password// {"token": "raw","newPassword": "stuff"}
    @PostMapping("/recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void recoverPassword(@Valid @RequestBody PasswordRecoveryRequest request) {
        userService.recoverPassword(request);
    }

    //POST // http://localhost:8081/auth/change-password {"currentPassword": "oldAdminPassword","newPassword": "newAdminPassword","adminPin": "******"}
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {
        userService.changePassword(
                authentication.getName(),
                request
        );
    }

    @GetMapping("/me")
    public LoginResponse me(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }
}