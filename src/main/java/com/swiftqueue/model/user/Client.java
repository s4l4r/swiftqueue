package com.swiftqueue.model.user;

import com.swiftqueue.model.AbstractEntity;
import com.swiftqueue.model.location.Address;
import com.swiftqueue.model.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AbstractEntity {

    @Column(name = "NAME")
    private String name;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private Set<Schedule> schedules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(getName(), client.getName()) && Objects.equals(getAddress(), client.getAddress()) && Objects.equals(getUserInfo(), client.getUserInfo()) && Objects.equals(getSchedules(), client.getSchedules());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getAddress(), getUserInfo(), getSchedules());
    }
}
