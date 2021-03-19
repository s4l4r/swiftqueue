package com.swiftqueue.service.user;

import com.swiftqueue.dto.auth.VerificationCodeDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.user.UserInfo;

import java.util.Optional;

public interface UserInfoService {

    String TYPE = "User";

    UserInfoDTO save(UserInfoDTO userInfoDTO) throws BusinessException;

    UserInfoDTO getById(Long userInfoId) throws ResourceNotFoundException;

    UserInfoDTO getByUsername(String username);

    Optional<UserInfo> getUserInfoByUserName(String userName);

    void updateUserInfo(UserInfoDTO userInfoDTO) throws BusinessException;

    boolean exists(long id);

    VerificationCodeDTO sendVerificationCodeAsSMS(String number);

    boolean verifyRegisteredUser(String verificationCode, UserInfoDTO userInfoDTO);
}
