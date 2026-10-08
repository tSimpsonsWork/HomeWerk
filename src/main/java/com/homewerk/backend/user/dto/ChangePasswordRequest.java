package com.homewerk.backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(

        @NotBlank
        String currentPassword,

        @NotBlank
        String newPassword,

        @Pattern(
                regexp = "\\d{6}",
                message = "Admin PIN must be exactly 6 digits."
        )
        String adminPin

) {
}