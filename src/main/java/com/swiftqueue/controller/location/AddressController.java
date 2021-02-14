package com.swiftqueue.controller.location;

import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.location.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{addressId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) throws ResourceNotFoundException {
        AddressDTO addressDTO = addressService.getById(addressId);
        if (addressDTO == null)
            throw new ResourceNotFoundException("Address", addressId);
        return ResponseEntity.ok(addressDTO);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody @Valid AddressDTO addressDTO) throws BusinessException {
        AddressDTO savedAddressDTO = addressService.save(addressDTO);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/addresses/")
                        .pathSegment(String.valueOf(savedAddressDTO.getId()))
                        .build().toUri()).build();
    }
}
