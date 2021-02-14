package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.CityDTO;
import com.swiftqueue.exception.server.ResourceNotFoundException;

import java.util.List;

public interface CityService {

    String TYPE = "City";

    CityDTO getById(Long cityId) throws ResourceNotFoundException;

    List<CityDTO> getAllByProvinceId(Long provinceId);
}
