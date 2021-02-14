package com.swiftqueue.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftqueue.dto.BaseDTO;
import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.dto.schedule.TimeSlotDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO extends BaseDTO {

    @NotBlank(message = "User first name must be provided")
    private String firstName;
    @NotBlank(message = "User last name must be provided")
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "User password must be provided")
    private String password;
    @NotBlank(message = "Username must be provided")
    private String username;
    @NotNull(message = "User type must be specified")
    private UserType userType;
    private AddressDTO address;
    private Set<TimeSlotDTO> timeslots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoDTO)) return false;
        if (!super.equals(o)) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getTimeslots(), that.getTimeslots()) && Objects.equals(getUserType(), that.getUserType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getPassword(), getUsername(), getAddress(), getTimeslots(), getUserType());
    }
}
