package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.User;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByEmail(currentPrincipalName);
    }

    @Override
    @Transactional
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userConverter.entityToDto(user);
    }

    @Override
    public UserDto findById(long userId) {
        User user = userRepository.findById(userId)
                                  .orElse(new User());

        return userConverter.entityToDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();

        return userConverter.entityToDto(userList);
    }

    @Override
    public List<UserDto> findAllByCustomerId() {
        UserDto userDto = getUser();
        List<User> customerEmployeesList = userRepository.findAllByCustomer_Id(userDto.getCustomer()
                                                                                      .getId());
        return userConverter.entityToDto(customerEmployeesList);
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.save(user);

        return userConverter.entityToDto(persistUser);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.findById(user.getId())
                                         .orElse(new User());

        persistUser.setAddress(user.getAddress());
        persistUser.setBirthday(user.getBirthday());
        persistUser.setEmail(user.getEmail());
        persistUser.setFirstName(user.getFirstName());
        persistUser.setLastName(user.getLastName());
        persistUser.setPassword(user.getPassword());
        persistUser.setUserRole(user.getUserRole());
        persistUser.setUserStatus(user.getUserStatus());
        persistUser.setCustomer(user.getCustomer());
        persistUser.setLocation(user.getLocation());

        return userConverter.entityToDto(persistUser);
    }
}
