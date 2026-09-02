package com.yss.cas.api.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
public record VerificationCodeProperties(
        @NotBlank String pepper,
        @NotNull Duration ttl,
        @Min(1) int maxAttempts,
        @NotNull Duration resendDelay
) {
}
