package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    List<UserDto> findAll(Pageable pageable);

    List<UserDto> findAllByCustomerId(Pageable pageable);

    UserDto create(UserDto userDto);

    UserDto create(CustomerDto customerDto);

    UserDto update(UserDto userDto);

    UserDto getUser();

    UserDto findByEmail(String email);

    String generatePassword();

    String encodePassword(String password);

    List<UserDto> updateStatus(List<Long> customerIds);

    List<UserDto> findAllByLocationId(Long locationId);

}
