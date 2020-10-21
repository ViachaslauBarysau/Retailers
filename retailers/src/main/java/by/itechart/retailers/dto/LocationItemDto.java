package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
public class LocationItemDto {
    private Long id;
    @Valid
    private ItemDto itemDto;
    @NotBlank(message = "Cost can't be empty.")
    @DecimalMin(value = "0.01", message = "Cost must be greater than 0.")
    private BigDecimal cost;
}
