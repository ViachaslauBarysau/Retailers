package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Role;
import by.itechart.retailers.entity.UserStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;

    @NotNull(message = "First name can't be empty.")
    @Size(min = 1, max = 20, message = "First name can be from 1 to 20 symbols.")
    private String firstName;

    @NotNull(message = "Last name can't be empty.")
    @Size(min = 1, max = 20, message = "Last name can be from 1 to 20 symbols.")
    private String lastName;

    @Valid
    private AddressDto addressDto;

    @Past(message = "Birthday can't be in the future.")
    private LocalDate birthday;

    @Valid
    private List<Role> userRole;

    @Email(message = "Wrong email format.")
    private String email;

    @Size(min = 6, max = 20, message = "Password must be from 6 to 20 symbols.")
    private String password;

    @NotBlank(message = "Status can't be empty.")
    private UserStatus userStatus;
}
