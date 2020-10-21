package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Reason;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class WriteOffActItemRecordDto {
    private Long id;
    @Valid
    private ItemDto itemDto;
    @Min(value = 1, message = "Item amount must be equals or greater than 1.")
    private Integer amount;
    @NotBlank(message = "Reason can't be empty.")
    private Reason reason;
}
