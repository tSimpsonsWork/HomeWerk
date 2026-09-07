package com.homewerk.backend.controller.user;

import com.homewerk.backend.user.dto.SignupRequest;
import com.homewerk.backend.user.dto.SignupResponse;
import com.homewerk.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //POST http://localhost:8080/auth/signup{"email": "test@example.com","displayName": "Test User","password": "Password123!"}
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return userService.signup(request);
    }
}