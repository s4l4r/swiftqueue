package com.swiftqueue.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    public static final String RESOURCE_ID = "swiftqueue-rest-api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/", "/**/actuator/**", "/**/provinces/all", "/**/cities/all/**", "/**/users/test/**",
                        "/**/users/enabled/**", "/**/clients/{clientId:\\d+}").permitAll()
                .antMatchers(HttpMethod.POST, "/**/otp/send-sms", "/**/otp/verify-sms", "/**/users",
                        "/**/clients/search/**").permitAll()
                .anyRequest().fullyAuthenticated();
        http.httpBasic()
                .and()
                .authorizeRequests()
                //TODO Does not check for the Role -- Should be fixed
                .antMatchers("/**/management/**").hasAuthority("ROLE_ADMIN");
    }
}
