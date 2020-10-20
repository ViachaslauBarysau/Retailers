package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private BigDecimal categoryTax;
}
