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
public class BillDto {

    private Long id;

    @Min(value = 0, message = "Wrong bill number.")
    private Integer billNumber;

    @Valid
    private LocationDto locationDto;

    @Valid
    private UserDto shopManager;

    @Past(message = "Registration date of bill cannot be in the future.")
    private LocalDateTime registrationDateTime;

    @Valid
    private List<BillItemRecordDto> billItemRecordDtoList;

    @Min(value = 1, message = "Item amount must be greater than 0.")
    private Integer totalItemAmount;

    @Min(value = 1, message = "Unit number must be greater than 0.")
    private Integer totalUnitNumber;
}
