package com.swiftqueue.model.schedule;

import com.swiftqueue.model.AbstractEntity;
import com.swiftqueue.model.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TIME_SLOTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserInfo userInfo;

    @Column(name = "DATE")
    private String date;

    @Column(name = "TIME")
    private String time;

    @Column(name = "RESERVED")
    private boolean reserved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        if (!super.equals(o)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return isReserved() == timeSlot.isReserved() && Objects.equals(getSchedule(), timeSlot.getSchedule()) && Objects.equals(getUserInfo(), timeSlot.getUserInfo()) && Objects.equals(getDate(), timeSlot.getDate()) && Objects.equals(getTime(), timeSlot.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSchedule(), getUserInfo(), getDate(), getTime(), isReserved());
    }
}
