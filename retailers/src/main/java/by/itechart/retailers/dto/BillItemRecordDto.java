package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
public class BillItemRecordDto {

    private Long id;

    @Valid
    private ItemDto itemDto;

    @Min(value = 1, message = "Item amount must be greater than 0.")
    private Integer itemAmount;

    @NotBlank(message = "Price can't be empty.")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    private BigDecimal itemPrice;
}
