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
    private static final String RESOURCE_ID = "resource-server-rest-api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**/provinces/all").permitAll()
                .antMatchers(HttpMethod.GET, "/**/cities/all/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**/users/test/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**/users").permitAll()
                .antMatchers(HttpMethod.POST, "/**/clients/search/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**/clients/{clientId:\\d+}").permitAll()
                .anyRequest().authenticated();
    }
}
