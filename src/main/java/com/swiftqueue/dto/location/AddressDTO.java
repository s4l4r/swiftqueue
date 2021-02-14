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

    @NotNull(message = "City must be provided for address")
    private CityDTO city;
    @NotBlank(message = "Street must be provided for address")
    private String street;
    @NotBlank(message = "Phone Number must be provided for address")
    private String phoneNumber;
    @NotBlank(message = "House Number must be provided for address")
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
