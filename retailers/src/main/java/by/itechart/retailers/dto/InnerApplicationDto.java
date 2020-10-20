package by.itechart.retailers.dto;

import by.itechart.retailers.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InnerApplicationDto {
    private Long id;
    private Integer applicationNumber;
    private LocationDto sourceLocation;
    private LocationDto destinationLocation;
    private UserDto creator;
    private UserDto updater;
    private LocalDateTime registrationDateTime;
    private LocalDateTime updatingDateTime;
    private ApplicationStatus applicationStatus;
    private List<ApplicationItemRecordDto> applicationItemRecordDtoList;
    private Integer totalItemAmount;
    private Integer totalUnitNumber;
}
