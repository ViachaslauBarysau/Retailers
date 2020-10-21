package by.itechart.retailers.dto;

import by.itechart.retailers.entity.LocationType;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class LocationDto {
    private Long id;
    @NotBlank(message = "Location identifier can't be empty.")
    @Size(min = 1, max = 20, message = "Identifier can be from 1 to 20 symbols.")
    private String identifier;
    @Valid
    private CustomerDto customerDto;
    @Valid
    private AddressDto addressDto;
    @Valid
    private List<LocationItemDto> locationItemDtoList;
    @Min(value = 1, message = "Total capacity must be greater than 0.")
    private Integer totalCapacity;
    @Min(value = 1, message = "Available capacity must be greater than 0.")
    private Integer availableCapacity;
    @NotBlank(message = "Location type can't be empty.")
    private LocationType locationType;
}
