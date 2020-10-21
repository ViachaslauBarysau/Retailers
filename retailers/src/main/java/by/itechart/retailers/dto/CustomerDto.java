package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
public class CustomerDto {
    private Long id;
    @Valid
    private List<LocationDto> locationDtoList;
    @Valid
    private List<CategoryDto> categoryDtoListList;
    @Valid
    private UserDto admin;
    @Valid
    private UserDto director;
}
