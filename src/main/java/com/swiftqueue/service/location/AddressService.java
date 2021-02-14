package com.swiftqueue.service.location;

import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;

public interface AddressService {

    String TYPE = "Address";

    AddressDTO getById(Long addressId) throws ResourceNotFoundException;

    AddressDTO save(AddressDTO addressDTO) throws BusinessException;
}
