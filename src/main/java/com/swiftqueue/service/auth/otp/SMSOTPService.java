package com.swiftqueue.service.auth.otp;

import com.swiftqueue.dto.auth.VerificationCodeDTO;
import com.swiftqueue.model.auth.VerificationCode;
import com.swiftqueue.repository.auth.VerificationCodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SMSOTPService {

    private final ModelMapper modelMapper;
    private final VerificationCodeRepository verificationCodeRepository;

    @Autowired
    public SMSOTPService(ModelMapper modelMapper, VerificationCodeRepository verificationCodeRepository) {
        this.modelMapper = modelMapper;
        this.verificationCodeRepository = verificationCodeRepository;
    }

    public VerificationCodeDTO sendVerificationMessage(String number) {
        VerificationCode verificationCode = verificationCodeRepository.save(new VerificationCode("c0de", number, LocalDateTime.now()));
        return modelMapper.map(verificationCode, VerificationCodeDTO.class);
    }

    public boolean verifyMessage(String code, String number) {
        return verificationCodeRepository.findByCodeAndPhoneNumber(code, number).isPresent();
    }
}
