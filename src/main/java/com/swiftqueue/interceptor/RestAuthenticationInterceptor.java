package com.swiftqueue.interceptor;

import com.swiftqueue.config.security.AESDecrypter;
import com.swiftqueue.config.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final AESDecrypter aesDecrypter;
    private final SecurityProperties securityProperties;

    public static final String USER_SIGN_UP_AUTH_HEADER = "SWIFT_QUEUE_AUTHORIZATION";

    public RestAuthenticationInterceptor(AESDecrypter aesDecrypter, SecurityProperties securityProperties) {
        this.aesDecrypter = aesDecrypter;
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws IOException {
        String encryptedValue = httpServletRequest.getHeader(USER_SIGN_UP_AUTH_HEADER);
        try {
            String decryptedValue = aesDecrypter.decrypt(securityProperties.getSecuredSharedKey(), encryptedValue);
            return decryptedValue.equals(securityProperties.getUserSignupToken());
        } catch (Exception ex) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "SWIFT_QUEUE_AUTH_MISSING");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        //Do nothing, only need the pre-handle part of the filter
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //Do nothing, only need the pre-handle part of the filter
    }
}
