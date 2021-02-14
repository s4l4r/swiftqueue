package com.swiftqueue.config.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "swiftqueue.security")
public class SecurityProperties {

    @NotBlank
    private String securedSharedKey;

    @NotBlank
    private String userSignupToken;
}
