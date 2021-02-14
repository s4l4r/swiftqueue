package com.swiftqueue.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftqueue.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO extends BaseDTO {

    @NotBlank(message = "City name must be provided")
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Province of the city must be provided")
    private ProvinceDTO province;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityDTO)) return false;
        if (!super.equals(o)) return false;
        CityDTO cityDTO = (CityDTO) o;
        return Objects.equals(getName(), cityDTO.getName()) &&
                Objects.equals(getProvince(), cityDTO.getProvince());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
