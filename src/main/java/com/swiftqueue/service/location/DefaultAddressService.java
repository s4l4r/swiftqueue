package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.dto.location.CityDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.location.Address;
import com.swiftqueue.repository.location.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAddressService implements AddressService {

    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final AddressRepository addressRepository;

    @Autowired
    public DefaultAddressService(ModelMapper modelMapper, CityService cityService, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDTO getById(Long id) throws ResourceNotFoundException {
        Address address = addressRepository.findOne(id);
        if (address == null)
            throw new ResourceNotFoundException(TYPE, id);
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    @Transactional
    public AddressDTO save(AddressDTO dto) throws BusinessException {
        CityDTO city;
        try {
            city = cityService.getById(dto.getCity().getId());
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No city found with ID: " + dto.getCity().getId());
        }
        Address address = modelMapper.map(dto, Address.class);
        address = addressRepository.save(address);
        address.getCity().setName(city.getName());
        return modelMapper.map(address, AddressDTO.class);
    }
}
