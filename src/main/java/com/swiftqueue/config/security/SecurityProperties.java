package com.swiftqueue.config.security;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swiftqueue.security")
public class SecurityProperties {

    @NotEmpty
    private String securedSharedKey;

    @NotEmpty
    private String userSignupToken;
}
