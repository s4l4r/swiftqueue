package com.swiftqueue.model.schedule;

import com.swiftqueue.model.AbstractEntity;
import com.swiftqueue.model.user.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SCHEDULES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends AbstractEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "FROM_DATE")
    private String fromDate;

    @Column(name = "TO_DATE")
    private String toDate;

    @Column(name = "FROM_TIME")
    private String fromTime;

    @Column(name = "TO_TIME")
    private String toTime;

    @Column(name = "INTERVAL_TIME")
    private int intervalTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private Client client;

    @OneToMany(mappedBy = "schedule", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<TimeSlot> timeSlots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        if (!super.equals(o)) return false;
        Schedule schedule = (Schedule) o;
        return getIntervalTime() == schedule.getIntervalTime() && Objects.equals(getName(), schedule.getName()) && Objects.equals(getFromDate(), schedule.getFromDate()) && Objects.equals(getToDate(), schedule.getToDate()) && Objects.equals(getFromTime(), schedule.getFromTime()) && Objects.equals(getToTime(), schedule.getToTime()) && Objects.equals(getClient(), schedule.getClient()) && Objects.equals(getTimeSlots(), schedule.getTimeSlots());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getFromDate(), getToDate(), getFromTime(), getToTime(), getIntervalTime());
    }
}
