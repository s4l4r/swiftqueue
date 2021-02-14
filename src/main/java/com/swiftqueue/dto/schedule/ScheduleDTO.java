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

    @NotBlank(message = "Schedule must have a name")
    private String name;
    @NotBlank(message = "Schedule from date must be provided")
    private String fromDate;
    @NotBlank(message = "Schedule to date must be provided")
    private String toDate;
    @NotBlank(message = "Schedule from time must be provided")
    private String fromTime;
    @NotBlank(message = "Schedule to time must be provided")
    private String toTime;
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "Interval must be an integer number")
    private int intervalTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Client must be provided for the schedule")
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
