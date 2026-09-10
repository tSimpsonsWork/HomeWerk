package com.homewerk.backend.user.service;

import com.homewerk.backend.user.dto.LoginResponse;
import org.springframework.security.core.Authentication;

public record LoginResult(
        Authentication authentication,
        LoginResponse response
) {}