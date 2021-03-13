package com.swiftqueue.dto.location;

import com.swiftqueue.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JsonFileProvinceDTO extends BaseDTO {
    Set<CityDTO> cities;
}
