package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {
    private Long id;
    @Valid
    private List<LocationDto> locationList;
    @Valid
    private List<CategoryDto> categoryList;
    @Valid
    private UserDto admin;
    @Valid
    private UserDto director;
}
