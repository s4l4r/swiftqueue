package com.swiftqueue.model.user;

import com.swiftqueue.model.AbstractEntity;
import com.swiftqueue.model.location.Address;
import com.swiftqueue.model.schedule.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends AbstractEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 800)
    private String password;

    @Column(name = "ROLE", length = 50)
    private String role;

    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;

    @OneToMany(mappedBy = "userInfo")
    private Set<TimeSlot> timeslots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return isEnabled() == userInfo.isEnabled() && Objects.equals(getFirstName(), userInfo.getFirstName()) && Objects.equals(getLastName(), userInfo.getLastName()) && Objects.equals(getUsername(), userInfo.getUsername()) && Objects.equals(getPassword(), userInfo.getPassword()) && Objects.equals(getRole(), userInfo.getRole()) && Objects.equals(getAddress(), userInfo.getAddress()) && Objects.equals(getTimeslots(), userInfo.getTimeslots());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getUsername(), getPassword(), getRole(), isEnabled(), getAddress());
    }
}
