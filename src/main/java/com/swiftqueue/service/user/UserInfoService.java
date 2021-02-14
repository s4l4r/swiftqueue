package com.swiftqueue.service.user;

import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.user.UserInfo;

import java.util.List;

public interface UserInfoService {

    String TYPE = "User";

    UserInfoDTO save(UserInfoDTO userInfoDTO) throws BusinessException;

    UserInfoDTO getById(Long userInfoId) throws ResourceNotFoundException;

    UserInfoDTO getByUsername(String username);

    UserInfo getUserInfoByUserName(String userName);

    void updateUserInfo(UserInfoDTO userInfoDTO) throws BusinessException;

    List<UserInfoDTO> getAllServiceUsers();

    boolean exists(long id);
}
