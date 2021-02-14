package com.swiftqueue.model.location;

import com.swiftqueue.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PROVINCES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Province extends AbstractEntity {

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private Set<City> cities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Province)) return false;
        if (!super.equals(o)) return false;
        Province province = (Province) o;
        return Objects.equals(getName(), province.getName()) &&
                Objects.equals(getCities(), province.getCities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
