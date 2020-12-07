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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        logger.info("Update status {}", userIds.toString());
        List<User> users = userRepository.findAllById(userIds);
        for (User user : users) {
            if (user.getLocation()
                    .getStatus() != DeletedStatus.DELETED) {
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
        logger.info("Find by birthday {}", date);
        List<User> users = userRepository.findAllByBirthdayAndUserStatus(date, Status.ACTIVE);
        return userConverter.entityToDto(users);
    }

    @Override
    @Transactional
    public List<UserDto> findAllByRole(Role role) {
        logger.info("Find by role {}", role.toString());
        List<User> users = userRepository.findAllByUserRoleAndUserStatus(role, Status.ACTIVE);
        return userConverter.entityToDto(users);
    }

    @Override
    public boolean emailExistsForCreate(String email) {
        logger.info("Check for existing email {}", email);
        return userRepository.findAllByEmail(email)
                             .size() != 0;
    }

    @Override
    public boolean emailExistsForUpdate(String email) {
        logger.info("Check for existing login {}", email);
        return userRepository.findAllByEmail(email)
                             .size() != 1;
    }

    @Override
    public boolean loginExistsForCreate(String login) {
        logger.info("Check for existing login {}", login);
        return userRepository.findAllByLogin(login)
                             .size() != 0;
    }

    @Override
    public boolean loginExistsForUpdate(String login) {
        logger.info("Check for existing login {}", login);
        return userRepository.findAllByLogin(login)
                             .size() != 1;
    }

    @Override
    @Transactional
    public UserDto getUser() {
        logger.info("Get user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByEmail(currentPrincipalName);
    }

    @Override
    @Transactional
    public UserDto findByEmail(String email) {
        logger.info("Find by email {}", email);
        User user = userRepository.findByEmail(email);
        return userConverter.entityToDto(user);
    }

    @Override
    public String generatePassword() {
        logger.info("Generate password");
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
        logger.info("Encode password");
        return encoder.encode(password);
    }

    @Override
    public UserDto findById(long userId) {
        logger.info("Find by id {}", userId);
        User user = userRepository.findById(userId)
                                  .orElse(new User());

        return userConverter.entityToDto(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = getUser();//доставать всех кроме админов
        Page<User> userPage = userRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                   .getId());
        List<UserDto> userDtos = userConverter.entityToDto(userPage.getContent());
        return new PageImpl<>(userDtos, pageable, userPage.getTotalElements());

    }

    @Override
    public List<UserDto> findAllByCustomerId(Pageable pageable) {
        logger.info("Find all by customer id");
        UserDto userDto = getUser();
        Page<User> customerEmployeesPage = userRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                                .getId());
        return userConverter.entityToDto(customerEmployeesPage.toList());
    }

    @Override
    public UserDto create(UserDto userDto) throws BusinessException {
        logger.info("Create by admin");
        User user = userConverter.dtoToEntity(userDto);
        if (emailExistsForCreate(user.getEmail())) {
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
        logger.info("Create by system admin");
        Customer customer = customerConverter.dtoToEntity(customerDto);
        if (emailExistsForCreate(customer.getEmail())) {
            logger.error("Not unique email {}", customer.getEmail());
            throw new BusinessException("Email should be unique");
        }
        User user = new User();
        user.setFirstName(customerDto.getName());
        user.setLastName(customerDto.getName());
        user.setEmail(customerDto.getEmail());
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
        logger.info("Update");
        User user = userConverter.dtoToEntity(userDto);
        if (emailExistsForUpdate(user.getEmail())) {
            logger.error("Not unique email {}", user.getEmail());
            throw new BusinessException("Email should be unique");
        }
        if (loginExistsForUpdate(user.getLogin())) {
            logger.error("Not unique login {}", user.getLogin());
            throw new BusinessException("Login should be unique");
        }
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


}
