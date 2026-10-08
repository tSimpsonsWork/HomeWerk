package com.homewerk.backend.config.admin;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "app.security")
public class AdminSecurityProperties {

    @Pattern(
            regexp = "\\d{6}",
            message = "ADMIN_PIN must be exactly 6 digits."
    )
    private String adminPin;
}