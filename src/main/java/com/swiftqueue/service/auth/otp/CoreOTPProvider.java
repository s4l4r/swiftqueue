package com.swiftqueue.service.auth.otp;

import com.swiftqueue.dto.auth.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoreOTPProvider implements OTPProvider {

    private final SMSOTPService smsotpService;

    @Autowired
    public CoreOTPProvider(SMSOTPService smsotpService) {
        this.smsotpService = smsotpService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public VerificationCodeDTO sendSMSOTP(String number) {
        return smsotpService.sendOTP(number);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean verifyOTP(String code, String number) {
        return smsotpService.verifyOTP(code, number);
    }
}
