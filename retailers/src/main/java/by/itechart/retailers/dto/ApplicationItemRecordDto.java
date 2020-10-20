package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ApplicationItemRecordDto {
    private Long id;
    private ItemDto itemDto;
    private Integer amount;
    private BigDecimal cost;
}
