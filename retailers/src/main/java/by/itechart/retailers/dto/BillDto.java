package by.itechart.retailers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillDto {

    private Long id;

    @Min(value = 0, message = "Wrong bill number.")
    private Integer billNumber;

    @Valid
    private LocationDto location;

    @Valid
    private UserDto shopManager;

    @Past(message = "Registration date of bill cannot be in the future.")
    private LocalDateTime registrationDateTime;

    @Valid
    private List<BillRecordDto> recordList;

    @Min(value = 1, message = "Product amount must be greater than 0.")
    private Integer totalProductAmount;

    @Min(value = 1, message = "Total price must be greater than 0.")
    private BigDecimal totalPrice;

    @Min(value = 1, message = "Total cost must be greater than 0.")
    private BigDecimal totalCost;

    @Valid
    private CustomerDto customer;
}
