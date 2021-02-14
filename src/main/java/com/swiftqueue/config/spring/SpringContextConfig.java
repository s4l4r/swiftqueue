package com.swiftqueue.config.spring;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.swiftqueue.config.security.AESDecrypter;
import com.swiftqueue.config.security.SecurityProperties;
import com.swiftqueue.interceptor.RestAuthenticationInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(restAuthenticationInterceptor())
//                .addPathPatterns("/**");
//    }
}

