package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Location;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class SupplierDto {
    private Long id;
    @NotBlank(message = "Name can't be empty.")
    @Size(min = 1, max = 20, message = "Name can be from 1 to 20 symbols.")
    private String fullName;
    @NotNull(message = "Identifier can't be empty.")
    @Size(min = 1, max = 20, message = "Identifier can be from 1 to 20 symbols.")
    private String identifier;
    @Valid
    private List<Location> wareHouseList;
}
