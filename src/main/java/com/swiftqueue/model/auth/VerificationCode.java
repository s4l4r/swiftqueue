package com.swiftqueue.model.auth;

import com.swiftqueue.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "VERIFICATION_CODES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode extends AbstractEntity {

    private String code;
    private String phoneNumber;
    private LocalDateTime generationTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationCode)) return false;
        if (!super.equals(o)) return false;
        VerificationCode that = (VerificationCode) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getGenerationTimestamp(), that.getGenerationTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode(), getPhoneNumber(), getGenerationTimestamp());
    }
}
