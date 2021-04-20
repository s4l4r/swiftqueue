package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.JsonFileProvinceDTO;
import com.swiftqueue.dto.location.ProvinceDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;

import java.util.List;

public interface ProvinceService {

    String TYPE = "Province";

    JsonFileProvinceDTO save(JsonFileProvinceDTO provinceDTO) throws BusinessException;

    ProvinceDTO getById(Long provinceId) throws ResourceNotFoundException;

    List<ProvinceDTO> getAll();
}
