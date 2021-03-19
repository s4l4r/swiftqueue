package com.swiftqueue.service.auth.otp.config;

import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "swiftqueue.otp.sms")
public class SMSOTPConfiguration {

    @NotBlank
    private String host;
}
