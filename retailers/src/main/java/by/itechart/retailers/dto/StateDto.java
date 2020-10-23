package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
public class StateDto {

    private Long id;

    @NotBlank(message = "State tax can't be empty.")
    @DecimalMin(value = "0", message = "Tax must be equals or greater than 0.")
    private BigDecimal stateTax;

    @NotBlank(message = "State name field can't be empty.")
    private String name;
}
