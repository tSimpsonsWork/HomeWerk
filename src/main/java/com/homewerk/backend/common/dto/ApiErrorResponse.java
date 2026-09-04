package com.homewerk.backend.common.dto;

import com.homewerk.backend.common.enums.ErrorCode;

public record ApiErrorResponse(
        ErrorCode error,
        String message
) {
}