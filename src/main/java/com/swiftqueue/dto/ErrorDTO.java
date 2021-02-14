package com.swiftqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private String errorType;
    private List<String> errors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDTO)) return false;
        ErrorDTO errorDTO = (ErrorDTO) o;
        return Objects.equals(getErrorType(), errorDTO.getErrorType()) &&
                Objects.equals(getErrors(), errorDTO.getErrors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getErrorType(), getErrors());
    }
}
