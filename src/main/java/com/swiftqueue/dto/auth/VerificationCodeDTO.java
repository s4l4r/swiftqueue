package com.swiftqueue.dto.auth;

import com.swiftqueue.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeDTO extends BaseDTO {

    private String code;
    private String phoneNumber;
    private LocalDateTime generationTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationCodeDTO)) return false;
        if (!super.equals(o)) return false;
        VerificationCodeDTO that = (VerificationCodeDTO) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getGenerationTimestamp(), that.getGenerationTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode(), getPhoneNumber(), getGenerationTimestamp());
    }
}
