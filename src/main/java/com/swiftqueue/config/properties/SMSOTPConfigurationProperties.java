package com.swiftqueue.config.properties;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swiftqueue.otp.sms")
public class SMSOTPConfigurationProperties {

    @NotEmpty
    private String providerUrl;

    @NotEmpty
    private String username;

    @NotEmpty
    private String token;
}
