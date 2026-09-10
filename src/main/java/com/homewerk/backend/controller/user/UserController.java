package com.homewerk.backend.controller.user;

import com.homewerk.backend.user.dto.LoginRequest;
import com.homewerk.backend.user.dto.LoginResponse;
import com.homewerk.backend.user.dto.SignupRequest;
import com.homewerk.backend.user.dto.SignupResponse;
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

    //POST http://localhost:8080/auth/signup{"email": "test@example.com","displayName": "Test User","password": "Password123!"}
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return userService.signup(request);
    }

    //POST http://localhost:8081/auth/login
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

    @GetMapping("/me")
    public LoginResponse me(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }
}