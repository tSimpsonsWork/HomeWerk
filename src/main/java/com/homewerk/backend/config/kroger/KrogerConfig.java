package com.homewerk.backend.config.kroger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KrogerProperties.class)
public class KrogerConfig {
}