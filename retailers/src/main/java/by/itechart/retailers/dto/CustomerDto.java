package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {
    private Long id;
    private List<LocationDto> locationList;
    private List<CategoryDto> categoryList;
    private UserDto admin;
    private UserDto director;
    @NotBlank(message = "Status can't be empty.")
    private Status customerStatus;
    private List<ProductDto> productList;
}
