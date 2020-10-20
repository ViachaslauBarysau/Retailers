package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StateDto {
    private Long id;
    private BigDecimal stateTax;
    private String name;
}
