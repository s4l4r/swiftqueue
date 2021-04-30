package com.swiftqueue.config.spring;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.swiftqueue.config.properties.SecurityProperties;
import com.swiftqueue.config.security.AESDecrypter;
import com.swiftqueue.interceptor.RestAuthenticationInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SpringContextConfig extends WebMvcConfigurerAdapter {

    private final SecurityProperties securityProperties;

    @Autowired
    public SpringContextConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public AESDecrypter aesDecrypter() {
        return new AESDecrypter();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestAuthenticationInterceptor restAuthenticationInterceptor() {
        return new RestAuthenticationInterceptor(aesDecrypter(), securityProperties);
    }

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }

    @Bean
    public DataSourceHealthIndicator dataSourceHealthIndicator(DataSource dataSource) {
        DataSourceHealthIndicator dataSourceHealthIndicator = new DataSourceHealthIndicator();
        dataSourceHealthIndicator.setDataSource(dataSource);
        return dataSourceHealthIndicator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restAuthenticationInterceptor())
                .addPathPatterns("/**");
    }
}

