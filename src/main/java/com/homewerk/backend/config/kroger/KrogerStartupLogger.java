package com.homewerk.backend.config.kroger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KrogerStartupLogger implements CommandLineRunner {

    private final KrogerProperties krogerProperties;

    @Override
    public void run(String... args) {

        if (krogerProperties.isConfigured()) {
            log.info("KROGER_INTEGRATION_CONFIGURED");
        } else {
            log.warn(
                    "KROGER_INTEGRATION_NOT_CONFIGURED - grocery provider unavailable"
            );
        }
    }
}