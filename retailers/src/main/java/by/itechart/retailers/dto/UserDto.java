package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Role;
import by.itechart.retailers.entity.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private AddressDto addressDto;
    private LocalDate birthday;
    private List<Role> userRole;
    private String email;
    private String password;
    private UserStatus userStatus;
}
