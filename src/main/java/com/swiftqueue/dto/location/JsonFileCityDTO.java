package com.swiftqueue.dto.location;

import com.swiftqueue.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JsonFileCityDTO extends BaseDTO {

    private String name;
    private long province;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonFileCityDTO)) return false;
        if (!super.equals(o)) return false;
        JsonFileCityDTO that = (JsonFileCityDTO) o;
        return getProvince() == that.getProvince() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getProvince());
    }
}
