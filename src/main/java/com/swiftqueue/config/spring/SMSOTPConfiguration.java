package com.swiftqueue.config.spring;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "swiftqueue.otp.sms")
public class SMSOTPConfiguration {

    //TODO Spring is not picking up the value and complains about it being null
//    @NotBlank
    private String host;
//    @NotBlank
    private String username;
//    @NotBlank
    private String token;
}
