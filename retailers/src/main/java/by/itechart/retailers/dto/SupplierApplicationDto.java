package by.itechart.retailers.dto;

import by.itechart.retailers.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SupplierApplicationDto {

    private Long id;

    @Min(value = 1, message = "Wrong application number.")
    private Integer applicationNumber;

    @Valid
    private SupplierWarehouseDto supplierWarehouse;

    @Valid
    private LocationDto destinationLocation;

    @Valid
    private UserDto creator;

    @Valid
    private UserDto updater;

    @Past(message = "Registration date of application can't be in the future.")
    private LocalDateTime registrationDateTime;

    @Past(message = "Updating date of application can't be in the future.")
    private LocalDateTime updatingDateTime;

    @NotBlank(message = "Status can't be empty.")
    private ApplicationStatus applicationStatus;

    @Valid
    private List<ApplicationRecordDto> recordsList;

    @Min(value = 0, message = "Product amount must be equals or greater than 0.")
    private Integer totalProductAmount;

    @Min(value = 0, message = "Unit number must be equals or greater than 0.")
    private Integer totalUnitNumber;
}
