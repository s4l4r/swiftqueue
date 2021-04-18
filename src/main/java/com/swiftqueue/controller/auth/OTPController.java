package com.swiftqueue.controller.auth;

import com.swiftqueue.dto.auth.OTPVerifyRequest;
import com.swiftqueue.dto.auth.VerificationCodeDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.auth.otp.OTPProvider;
import com.swiftqueue.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/otp")
public class OTPController {

    private final OTPProvider otpProvider;
    private final UserInfoService userInfoService;

    @Autowired
    public OTPController(OTPProvider otpProvider, UserInfoService userInfoService) {
        this.otpProvider = otpProvider;
        this.userInfoService = userInfoService;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<VerificationCodeDTO> sendSMSOTP(@RequestParam("username") String username) {
        return ResponseEntity.ok(otpProvider.sendSMSOTP(username));
    }

    @PostMapping("/verify-sms")
    public ResponseEntity<Boolean> verifySMSOTP(@RequestBody @Valid OTPVerifyRequest otpVerifyRequest) throws ResourceNotFoundException, BusinessException {
        UserInfoDTO userInfoDTO = otpVerifyRequest.getUserInfo().getId() != 0
                ? userInfoService.getById(otpVerifyRequest.getUserInfo().getId())
                : userInfoService.getByUsername(otpVerifyRequest.getUserInfo().getUsername());
        if (!otpProvider.verifyOTP(otpVerifyRequest.getCode(), userInfoDTO.getUsername()))
            return ResponseEntity.ok(false);
        userInfoService.enableUser(userInfoDTO);
        return ResponseEntity.ok(true);
    }
}
