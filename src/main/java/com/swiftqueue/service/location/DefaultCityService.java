package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.CityDTO;
import com.swiftqueue.dto.location.ProvinceDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.location.City;
import com.swiftqueue.model.location.Province;
import com.swiftqueue.repository.location.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCityService implements CityService {

    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final ProvinceService provinceService;

    @Autowired
    public DefaultCityService(ModelMapper modelMapper, CityRepository cityRepository, ProvinceService provinceService) {
        this.modelMapper = modelMapper;
        this.cityRepository = cityRepository;
        this.provinceService = provinceService;
    }

    @Override
    @Transactional(readOnly = true)
    public CityDTO getById(Long id) throws ResourceNotFoundException {
        City city = cityRepository.findOne(id);
        if (city == null)
            throw new ResourceNotFoundException(TYPE, id);
        return modelMapper.map(city, CityDTO.class);
    }

    @Transactional
    public CityDTO save(CityDTO dto) throws ResourceNotFoundException, BusinessException {
        ProvinceDTO province;
        try {
            province = provinceService.getById(dto.getProvince().getId());
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No Province found with ID: " + dto.getProvince().getId());
        }
        City city = modelMapper.map(dto, City.class);
        city = cityRepository.save(city);
        city.getProvince().setName(province.getName());
        return modelMapper.map(city, CityDTO.class);
    }

    @Transactional(readOnly = true)
    public List<CityDTO> getAllByProvinceId(Long provinceId) {
        Province province = Province.builder().id(provinceId).build();
        return cityRepository.findAllByProvince(province)
                .stream().map(city -> modelMapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }
}
