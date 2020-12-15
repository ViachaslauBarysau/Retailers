package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Role;
import by.itechart.retailers.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    @NotNull(message = "First name can't be empty.")
    @Size(min = 2, max = 20, message = "First name can be from 2 to 20 symbols.")
    private String firstName;

    @NotNull(message = "Last name can't be empty.")
    @Size(min = 2, max = 20, message = "Last name can be from 2 to 20 symbols.")
    private String lastName;

    @Valid
    private AddressDto address;

    @Past(message = "Birthday can't be in the future.")
    private LocalDate birthday;

    @Valid
    private List<Role> userRole = new ArrayList<>();

    @Email(message = "Wrong email format.")
    private String email;

    @Size(min = 6, max = 20, message = "Password must be from 6 to 20 symbols.")
    private String password;

    @NotBlank(message = "Status can't be empty.")
    private Status userStatus;

    @Valid
    private LocationDto location;

    @Valid
    private CustomerDto customer;

    @Size(min = 3, max = 20, message = "Password must be from 3 to 20 symbols.")
    private String login;
}
