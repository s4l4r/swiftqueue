package com.swiftqueue.model.location;

import com.swiftqueue.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ADDRESSES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    private City city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        if (!super.equals(o)) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getPhoneNumber(), address.getPhoneNumber()) &&
                Objects.equals(getHouseNumber(), address.getHouseNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCity(), getStreet(), getPhoneNumber(), getHouseNumber());
    }
}
