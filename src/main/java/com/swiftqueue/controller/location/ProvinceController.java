package com.swiftqueue.controller.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftqueue.dto.location.CityDTO;
import com.swiftqueue.dto.location.JsonFileCityDTO;
import com.swiftqueue.dto.location.ProvinceDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.location.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.swiftqueue.service.location.ProvinceService.TYPE;

@RestController
@RequestMapping("/api/v1/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/{provinceId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<ProvinceDTO> getProvinceById(@PathVariable Long provinceId) throws ResourceNotFoundException {
        ProvinceDTO provinceDTO = provinceService.getById(provinceId);
        if (provinceDTO == null)
            throw new ResourceNotFoundException(TYPE, provinceId);
        return ResponseEntity.ok(provinceDTO);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<ProvinceDTO> saveProvince(@RequestBody  @Valid ProvinceDTO provinceDTO) throws BusinessException {
        ProvinceDTO savedProvinceDTO = provinceService.save(provinceDTO);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/provinces/")
                        .pathSegment(String.valueOf(savedProvinceDTO.getId()))
                        .build().toUri()).build();
    }

    @PostMapping("/re-import-locations")
    @PreAuthorize("#oauth2.hasScope('trust')")
    public ResponseEntity<List<ProvinceDTO>> reImportLocations() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ProvinceDTO> provinceDTOS = mapper.readValue(new ClassPathResource("/location/provinces.json").getFile(), new TypeReference<List<ProvinceDTO>>() {});
        List<JsonFileCityDTO> jsonFileCityDTOS = mapper.readValue(new ClassPathResource("/location/cities.json").getFile(), new TypeReference<List<JsonFileCityDTO>>(){});
        provinceDTOS.forEach(provinceDTO -> provinceDTO.setCities(jsonFileCityDTOS.stream()
                .filter(city -> city.getProvince() == provinceDTO.getId())
                .map(city -> CityDTO.builder().name(city.getName()).build())
                .collect(Collectors.toSet())));
        provinceDTOS.forEach(provinceDTO -> provinceDTO.setId(0));
        return ResponseEntity.ok(provinceDTOS.stream()
                .map(provinceDTO -> {
                    try {
                        return provinceService.save(provinceDTO);
                    } catch (BusinessException e) {
                        //Persisting should rollback
                        throw new IllegalStateException();
                    }
                }).collect(Collectors.toList()));
    }
}
