package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.ProvinceDTO;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.location.Province;
import com.swiftqueue.repository.location.ProvinceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultProvinceService implements ProvinceService {

    private final ModelMapper modelMapper;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public DefaultProvinceService(ProvinceRepository provinceRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.provinceRepository = provinceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvinceDTO getById(Long id) throws ResourceNotFoundException {
        Province province = provinceRepository.findOne(id);
        if (province == null)
            throw new ResourceNotFoundException(TYPE, id);
        return modelMapper.map(province, ProvinceDTO.class);
    }

    @Transactional
    public ProvinceDTO save(ProvinceDTO dto) {
        Province province = modelMapper.map(dto, Province.class);
        province.getCities().forEach(city -> city.setProvince(province));
        return modelMapper.map(provinceRepository.save(province), ProvinceDTO.class);
    }
}
