package com.swiftqueue.service.user;

import com.swiftqueue.model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Qualifier("defaultUserDetails")
public class DefaultUserServiceDetails implements UserDetailsService {

    private final DefaultUserInfoService userInfoService;

    @Autowired
    public DefaultUserServiceDetails(DefaultUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) {
        UserInfo userInfo = userInfoService.getUserInfoByUserName(s);
        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
        return new User(userInfo.getUsername(), userInfo.getPassword(), Collections.singletonList(authority));
    }
}
