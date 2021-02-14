package com.swiftqueue.dto.user;

import com.swiftqueue.dto.BaseDTO;
import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.dto.schedule.ScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends BaseDTO {

    @NotNull(message = "Client must have a name")
    private String name;
    private AddressDTO address;
    @NotNull(message = "Client must have a user")
    private UserInfoDTO userInfo;
    private Set<ScheduleDTO> schedules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDTO)) return false;
        if (!super.equals(o)) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(getName(), clientDTO.getName()) && Objects.equals(getAddress(), clientDTO.getAddress()) && Objects.equals(getUserInfo(), clientDTO.getUserInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getAddress(), getUserInfo());
    }
}
