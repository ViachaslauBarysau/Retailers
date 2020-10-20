package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BillDto {
    private Long id;
    private Integer billNumber;
    private LocationDto locationDto;
    private UserDto shopManager;
    private LocalDateTime registrationDateTime;
    private List<BillItemRecordDto> billItemRecordDtoList;
    private Integer totalItemAmount;
    private Integer totalUnitNumber;
}
