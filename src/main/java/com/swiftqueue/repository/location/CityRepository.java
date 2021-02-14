package com.swiftqueue.repository.location;

import com.swiftqueue.model.location.City;
import com.swiftqueue.model.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByProvince(Province province);
}
