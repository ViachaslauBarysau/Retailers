package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Role;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    Page<UserDto> findAll(Pageable pageable);

    List<UserDto> findAllByCustomerId(Pageable pageable);

    UserDto create(UserDto userDto) throws BusinessException;

    UserDto create(CustomerDto customerDto) throws BusinessException;

    UserDto update(UserDto userDto);

    UserDto getUser();

    UserDto findByEmail(String email);

    String generatePassword();

    String encodePassword(String password);

    List<UserDto> updateStatus(List<Long> userIds);

    List<UserDto> findByBirthday(LocalDate date);

    List<UserDto> findAllByRole(Role role);

    boolean emailExistsForCreate(String email);

    boolean emailExistsForUpdate(String email);

    boolean loginExistsForCreate(String login);

    boolean loginExistsForUpdate(String login);


}
