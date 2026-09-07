package com.homewerk.backend.user.dto;

import com.homewerk.backend.user.enums.UserStatus;

public record SignupResponse(
        String email,
        String displayName,
        UserStatus status
) {
}