package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WriteOffActDto {

    private Long id;

    @Valid
    private List<WriteOffActRecordDto> writeOffActRecords;

    @Past(message = "Date and time of act can't be in the future.")
    private LocalDateTime actDateTime;

    @Min(value = 1, message = "Product amount must be equals or greater than 1.")
    private Integer totalProductAmount;

    private BigDecimal totalProductSum;
}
