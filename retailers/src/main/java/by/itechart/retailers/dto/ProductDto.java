package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {
    private Long id;
    @Min(value = 0, message = "Wrong upc number.")
    private Integer upc;
    @NotBlank(message = "Label can't be empty.")
    private String label;
    private CategoryDto category;
    @Min(value = 1, message = "Product volume must be greater than 0.")
    private Integer volume;
}
