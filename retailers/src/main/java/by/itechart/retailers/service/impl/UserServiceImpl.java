package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.interfaces.SendingCredentialsService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final static String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    private final static String specialCharacters = "!@#$";
    private final static String numbers = "1234567890";
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CustomerConverter customerConverter;
    private final BCryptPasswordEncoder encoder;
    private final SendingCredentialsService sendingCredentialsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, CustomerConverter customerConverter, @Lazy BCryptPasswordEncoder encoder, SendingCredentialsService sendingCredentialsService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.customerConverter = customerConverter;
        this.encoder = encoder;
        this.sendingCredentialsService = sendingCredentialsService;
    }

    @Override
    public List<UserDto> updateStatus(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        for (User user : users) {
            if(user.getLocation().getStatus()!= DeletedStatus.DELETED) {
                if (user.getUserStatus()
                        .equals(Status.ACTIVE)) {
                    user.setUserStatus(Status.DISABLED);
                } else {
                    user.setUserStatus(Status.ACTIVE);
                }
                userRepository.save(user);
            }
        }
        return userConverter.entityToDto(users);
    }

    @Override
    @Transactional
    public List<UserDto> findByBirthday(LocalDate date) {
        List<User> users = userRepository.findAllByBirthdayAndUserStatus(date, Status.ACTIVE);
        return userConverter.entityToDto(users);
    }

    @Override
    @Transactional
    public List<UserDto> findAllByRole(Role role) {
        List<User> users = userRepository.findAllByUserRoleAndUserStatus(role, Status.ACTIVE);
        return userConverter.entityToDto(users);
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
    public String generatePassword() {
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] charPassword = new char[8];

        charPassword[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        charPassword[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        charPassword[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        charPassword[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < 8; i++) {
            charPassword[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(charPassword);
    }

    @Override
    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public UserDto findById(long userId) {
        User user = userRepository.findById(userId)
                                  .orElse(new User());

        return userConverter.entityToDto(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserDto> userDtos = userConverter.entityToDto(userPage.getContent());
        return new PageImpl<>(userDtos, pageable, userPage.getTotalElements());

    }

    @Override
    public List<UserDto> findAllByCustomerId(Pageable pageable) {
        UserDto userDto = getUser();
        Page<User> customerEmployeesPage = userRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                                .getId());
        return userConverter.entityToDto(customerEmployeesPage.toList());
    }

    @Override
    public UserDto create(UserDto userDto) throws BusinessException {
        User user = userConverter.dtoToEntity(userDto);
        if (emailExists(user.getEmail())) {
            throw new BusinessException("Email should be unique");
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        User persistUser = userRepository.save(user);
        persistUser.setPassword(password);
        userDto = userConverter.entityToDto(persistUser);
        sendingCredentialsService.send(userDto);
        return userDto;
    }

    @Override
    public UserDto create(CustomerDto customerDto) throws BusinessException {
        Customer customer = customerConverter.dtoToEntity(customerDto);
        if (emailExists(customer.getEmail())) {
            throw new BusinessException("Email should be unique");
        }
        User user = new User();
        user.setFirstName(customerDto.getName());
        user.setLastName(customerDto.getName());
        user.setEmail(customerDto.getEmail());
        user.setLogin(customerDto.getEmail());
        user.setCustomer(customer);
        String password = generatePassword();
        user.setPassword(password);
        sendingCredentialsService.send(userConverter.entityToDto(user));
        user.setPassword(encodePassword(password));
        user.setUserStatus(Status.ACTIVE);
        user.setUserRole(Collections.singletonList(Role.ADMIN));
        userRepository.save(user);
        return userConverter.entityToDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.findById(user.getId())
                                         .orElse(new User());

        persistUser.setAddress(user.getAddress());
        persistUser.setBirthday(user.getBirthday());
        persistUser.setEmail(user.getEmail());
        persistUser.setLogin(user.getLogin());
        persistUser.setFirstName(user.getFirstName());
        persistUser.setLastName(user.getLastName());
        persistUser.setPassword(user.getPassword());
        persistUser.setUserRole(user.getUserRole());
        persistUser.setUserStatus(user.getUserStatus());
        persistUser.setCustomer(user.getCustomer());
        persistUser.setLocation(user.getLocation());
        persistUser.setLogin(user.getLogin());
        persistUser = userRepository.save(persistUser);

        return userConverter.entityToDto(persistUser);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findUserByEmail(email)
                             .isPresent();
    }

    @Override
    public boolean loginExists(String login) {
        return userRepository.findUserByLogin(login)
                             .isPresent();
    }

}
