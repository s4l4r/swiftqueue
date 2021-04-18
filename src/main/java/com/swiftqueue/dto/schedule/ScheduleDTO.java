package com.swiftqueue.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftqueue.dto.BaseDTO;
import com.swiftqueue.dto.user.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO extends BaseDTO {

    @NotBlank(message = "{validation.schedule.name.notEmpty}")
    private String name;
    @NotBlank(message = "{validation.schedule.fromDate.notEmpty}")
    private String fromDate;
    @NotBlank(message = "{validation.schedule.toDate.notEmpty}")
    private String toDate;
    @NotBlank(message = "{validation.schedule.fromTime.notEmpty}")
    private String fromTime;
    @NotBlank(message = "{validation.schedule.toTime.notEmpty}")
    private String toTime;
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{validation.schedule.interval.digits}")
    private int intervalTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{validation.schedule.client.notEmpty}")
    private ClientDTO client;
    private Set<TimeSlotDTO> timeSlots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDTO)) return false;
        if (!super.equals(o)) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return getIntervalTime() == that.getIntervalTime() && Objects.equals(getName(), that.getName()) && Objects.equals(getFromDate(), that.getFromDate()) && Objects.equals(getToDate(), that.getToDate()) && Objects.equals(getFromTime(), that.getFromTime()) && Objects.equals(getToTime(), that.getToTime()) && Objects.equals(getClient(), that.getClient()) && Objects.equals(getTimeSlots(), that.getTimeSlots());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getFromDate(), getToDate(), getFromTime(), getToTime(), getIntervalTime(), getClient());
    }
}
