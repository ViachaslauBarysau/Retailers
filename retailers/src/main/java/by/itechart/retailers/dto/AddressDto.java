package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AddressDto {
    private Long id;
    @Valid
    private StateDto stateDto;
    @NotBlank(message = "City is a mandatory field.")
    private String city;
    @NotBlank(message = "Address is a mandatory field.")
    private String firstAddressLine;
    private String secondAddressLine;
}
