package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WriteOffActDto {
    private Long id;
    private List<WriteOffActItemRecordDto> writeOffActItemRecordDtoList;
    private LocalDateTime actDateTime;
    private Integer totalItemAmount;
}
