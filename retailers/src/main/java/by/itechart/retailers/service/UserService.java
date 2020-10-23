package by.itechart.retailers.service;

import by.itechart.retailers.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    List<UserDto> findAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);
}
