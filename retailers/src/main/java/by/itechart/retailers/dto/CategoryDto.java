package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Customer;
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
public class CategoryDto {
    private Long id;
    @NotBlank(message = "Amount field can't be empty.")
    private String name;
    @NotBlank(message = "Category tax can't be empty.")
    @DecimalMin(value = "0", message = "Tax must be equals or greater than 0.")
    private BigDecimal categoryTax;
    @Valid
    private CustomerDto customer;
}
