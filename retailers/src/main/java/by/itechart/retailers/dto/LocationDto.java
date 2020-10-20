package by.itechart.retailers.dto;

import by.itechart.retailers.entity.LocationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LocationDto {
    private Long id;
    private String identifier;
    private CustomerDto customerDto;
    private AddressDto addressDto;
    private List<LocationItemDto> locationItemDtoList;
    private Integer totalCapacity;
    private Integer availableCapacity;
    private LocationType locationType;
}
