package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private Long id;
    private StateDto stateDto;
    private String city;
    private String firstAddressLine;
    private String secondAddressLine;
}
