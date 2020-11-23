package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {

    private Long id;

    @NotNull(message = "Customer name can't be empty.")
    @Size(min = 1, max = 20, message = "Customer name can be from 1 to 20 symbols.")
    private String name;

    @Email(message = "Wrong email format.")
    private String email;

    @Past(message = "Registration date can't be in the future.")
    private LocalDate registrationDate;

    @Valid
    private List<CategoryDto> categoryList;

    @NotBlank(message = "Status can't be empty.")
    private Status customerStatus;
}
