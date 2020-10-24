package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Reason;
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
public class WriteOffActItemRecordDto {
    private Long id;
    @Valid
    private ItemDto item;
    @Min(value = 1, message = "Item amount must be equals or greater than 1.")
    private Integer amount;
    @NotBlank(message = "Reason can't be empty.")
    private Reason reason;
}
