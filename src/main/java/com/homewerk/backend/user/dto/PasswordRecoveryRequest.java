package com.homewerk.backend.user.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordRecoveryRequest(

        @NotBlank
        String token,

        @NotBlank
        String newPassword

) {
}