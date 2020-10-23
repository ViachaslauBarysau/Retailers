package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CustomerConverter customerConverter) {
        this.userRepository = userRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public UserDto findById(long userId) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public UserDto create(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }
}
