package com.homewerk.backend.user.dto;

import com.homewerk.backend.user.enums.UserRole;

public record LoginResponse(
        Long id,
        String displayName,
        String email,
        UserRole role
) {}