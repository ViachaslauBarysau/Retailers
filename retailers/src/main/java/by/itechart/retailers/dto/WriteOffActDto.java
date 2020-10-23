package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WriteOffActDto {

    private Long id;

    @Valid
    private List<WriteOffActItemRecordDto> writeOffActItemRecordDtoList;

    @Past(message = "Date and time of act can't be in the future.")
    private LocalDateTime actDateTime;

    @Min(value = 1, message = "Item amount must be equals or greater than 1.")
    private Integer totalItemAmount;
}
