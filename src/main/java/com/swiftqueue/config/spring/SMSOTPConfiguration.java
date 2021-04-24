package com.swiftqueue.config.spring;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swiftqueue.otp.sms")
public class SMSOTPConfiguration {

    @NotEmpty
    private String providerUrl;
    @NotEmpty
    private String username;
    @NotEmpty
    private String token;
}
