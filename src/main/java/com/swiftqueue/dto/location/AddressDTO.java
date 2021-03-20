package com.swiftqueue.dto.location;

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
public class AddressDTO extends BaseDTO {

    @NotNull(message = "{validation.address.city.notEmpty}")
    private CityDTO city;
    @NotBlank(message = "{validation.address.street.notEmpty}")
    private String street;
    @NotBlank(message = "{validation.address.phoneNumber.notEmpty}")
    private String phoneNumber;
    @NotBlank(message = "{validation.address.houseNumber.notEmpty}")
    private String houseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;
        if (!super.equals(o)) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getHouseNumber(), that.getHouseNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCity(), getStreet(), getPhoneNumber(), getHouseNumber());
    }
}
