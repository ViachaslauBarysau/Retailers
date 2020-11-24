package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    List<UserDto> findAll(Pageable pageable);

    List<UserDto> findAllByCustomerId(Pageable pageable);

    UserDto create(UserDto userDto) throws NotUniqueDataException;

    UserDto create(CustomerDto customerDto) throws NotUniqueDataException;

    UserDto update(UserDto userDto);

    UserDto getUser();

    UserDto findByEmail(String email);

    String generatePassword();

    String encodePassword(String password);

    List<UserDto> updateStatus(List<Long> userIds);

    boolean emailExists(String email);

    boolean loginExists(String login);

}
