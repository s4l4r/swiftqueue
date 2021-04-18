package com.swiftqueue.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftqueue.dto.BaseDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO extends BaseDTO implements Comparable<TimeSlotDTO> {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{validation.timeSlot.schedule.notEmpty}")
    private ScheduleDTO schedule;
    @NotBlank(message = "{validation.timeSlot.time.notEmpty}")
    private String time;
    @NotBlank(message = "{validation.timeSlot.date.notEmpty}")
    private String date;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserInfoDTO userInfo;
    private boolean reserved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlotDTO)) return false;
        if (!super.equals(o)) return false;
        TimeSlotDTO that = (TimeSlotDTO) o;
        return isReserved() == that.isReserved() && Objects.equals(getSchedule(), that.getSchedule()) && Objects.equals(getTime(), that.getTime()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getUserInfo(), that.getUserInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSchedule(), getTime(), getDate(), isReserved());
    }

    @Override
    public int compareTo(TimeSlotDTO o) {
        return Comparator.comparing(TimeSlotDTO::getDate)
                .thenComparing(TimeSlotDTO::getTime)
                .compare(this, o);
    }
}
