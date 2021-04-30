package com.swiftqueue;

import com.swiftqueue.config.properties.SMSOTPConfigurationProperties;
import com.swiftqueue.config.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({SecurityProperties.class, SMSOTPConfigurationProperties.class})
public class SwiftQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftQueueApplication.class, args);
	}
}
