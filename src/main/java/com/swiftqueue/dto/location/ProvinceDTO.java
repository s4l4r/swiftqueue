package com.swiftqueue.dto.location;

import com.swiftqueue.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO extends BaseDTO {

    @NotBlank(message = "{validation.province.name.notEmpty}")
    private String name;
    @NotEmpty(message = "{validation.province.cities.notEmpty}")
    private Set<CityDTO> cities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProvinceDTO)) return false;
        if (!super.equals(o)) return false;
        ProvinceDTO that = (ProvinceDTO) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCities(), that.getCities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
