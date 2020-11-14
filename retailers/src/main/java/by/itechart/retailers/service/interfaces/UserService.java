package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    List<UserDto> findAll();

    List<UserDto> findAllByCustomerId();

    UserDto create(UserDto userDto);

    UserDto create(CustomerDto customerDto);

    UserDto update(UserDto userDto);

    UserDto getUser();

    UserDto findByEmail(String email);

    String generatePassword();
    String encodePassword(String password);

}
