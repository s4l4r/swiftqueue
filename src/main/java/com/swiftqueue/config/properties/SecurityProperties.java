package com.swiftqueue.config.properties;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swiftqueue.security")
public class SecurityProperties {

    @NotEmpty
    private String securedSharedKey;

    @NotEmpty
    private String userSignupToken;
}
