package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressDto {

    private Long id;

    @Valid
    private StateDto state;

    @NotBlank(message = "City is a mandatory field.")
    private String city;

    @NotBlank(message = "Address is a mandatory field.")
    private String firstAddressLine;

    private String secondAddressLine;
}
