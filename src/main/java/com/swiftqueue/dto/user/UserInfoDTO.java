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

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO extends BaseDTO {

    @NotBlank(message = "{validation.userInfo.firstName.notEmpty}")
    private String firstName;
    @NotBlank(message = "{validation.userInfo.lastName.notEmpty}")
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{validation.userInfo.password.notEmpty}")
    private String password;
    @NotBlank(message = "{validation.userInfo.username.notEmpty}")
    private String username;
    private AddressDTO address;
    private Set<TimeSlotDTO> timeslots;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoDTO)) return false;
        if (!super.equals(o)) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getPassword(),
                that.getPassword()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getTimeslots(), that.getTimeslots()) && Objects.equals(isEnabled(), that.isEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getPassword(), getUsername(), getAddress(), getTimeslots(), isEnabled());
    }
}
