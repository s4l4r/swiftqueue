package com.swiftqueue.service.auth.otp;

import com.swiftqueue.dto.auth.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreOTPProvider implements OTPProvider {

    private final SMSOTPService smsotpService;

    @Autowired
    public CoreOTPProvider(SMSOTPService smsotpService) {
        this.smsotpService = smsotpService;
    }

    @Override
    public VerificationCodeDTO sendVerificationMessageUsingSMS(String number) {
        return smsotpService.sendVerificationMessage(number);
    }

    @Override
    public boolean verifySMSVerificationMessage(String code, String number) {
        return smsotpService.verifyMessage(code, number);
    }
}
