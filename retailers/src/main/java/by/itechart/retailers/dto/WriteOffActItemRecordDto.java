package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Reason;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WriteOffActItemRecordDto {
    private Long id;
    private ItemDto itemDto;
    private Integer amount;
    private Reason reason;
}
