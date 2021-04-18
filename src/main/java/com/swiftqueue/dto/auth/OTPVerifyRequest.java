package com.swiftqueue.dto.auth;

import com.swiftqueue.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OTPVerifyRequest implements Serializable {

    @NotBlank(message = "{validation.otpVerifyRequest.code.notEmpty}")
    private String code;
    @NotNull(message = "{validation.otpVerifyRequest.userInfo.notEmpty}")
    private UserInfoDTO userInfo;
}
