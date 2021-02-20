package com.swiftqueue.controller.location;

import com.swiftqueue.dto.location.CityDTO;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.location.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) throws ResourceNotFoundException {
        CityDTO cityDTO = cityService.getById(id);
        return ResponseEntity.ok(cityDTO);
    }

    @GetMapping("/all/{provinceId}")
    public ResponseEntity<List<CityDTO>> getCitiesByProvinceId(@PathVariable Long provinceId) {
        return ResponseEntity.ok(cityService.getAllByProvinceId(provinceId));
    }
}
