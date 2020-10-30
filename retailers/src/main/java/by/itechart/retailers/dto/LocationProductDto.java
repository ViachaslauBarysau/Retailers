package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationProductDto {
    private Long id;
    @Valid
    private ProductDto product;
    @NotBlank(message = "Cost can't be empty.")
    @DecimalMin(value = "0.01", message = "Cost must be greater than 0.")
    private BigDecimal cost;
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    private BigDecimal price;
}
