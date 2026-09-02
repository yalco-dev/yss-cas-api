package com.yss.cas.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties
public record AppProperties(
        VerificationCodeProperties verificationCode
) {
}
