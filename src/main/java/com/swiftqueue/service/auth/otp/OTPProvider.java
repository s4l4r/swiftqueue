package com.swiftqueue.service.auth.otp;

import com.swiftqueue.dto.auth.VerificationCodeDTO;

public interface OTPProvider {

    //SMS Related Operations

    /**
     * Sends an SMS message containing random a verification code to the number specified.
     * @param number The number to send the message to
     * @return The random generated verification code that has been sent
     */
    VerificationCodeDTO sendSMSOTP(String number);

    /**
     * Verifies an input verification code from user
     * @param code The verification code to be verified
     * @param number The number for which the code had been sent
     * @return True if verified otherwise false
     */
    boolean verifyOTP(String code, String number);
}
