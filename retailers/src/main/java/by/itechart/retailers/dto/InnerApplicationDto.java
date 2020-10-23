package by.itechart.retailers.dto;

import by.itechart.retailers.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InnerApplicationDto {

    private Long id;

    @Min(value = 0, message = "Wrong application number.")
    private Integer applicationNumber;

    @Valid
    private LocationDto sourceLocation;

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

    private List<ApplicationItemRecordDto> applicationItemRecordDtoList;

    @Min(value = 0, message = "Item amount must be equals or greater than 0.")
    private Integer totalItemAmount;

    @Min(value = 0, message = "Unit number must be equals or greater than 0.")
    private Integer totalUnitNumber;
}
