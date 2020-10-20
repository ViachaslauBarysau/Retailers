package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private List<LocationDto> locationDtoList;
    private List<CategoryDto> categoryDtoListList;
    private UserDto admin;
    private UserDto director;
}
