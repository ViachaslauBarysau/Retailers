package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Role;
import by.itechart.retailers.service.interfaces.SendingCongratulationsService;
import by.itechart.retailers.service.interfaces.SendingService;
import by.itechart.retailers.service.interfaces.UserService;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SendingCongratulationsServiceImpl implements SendingCongratulationsService {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(SendingCongratulationsServiceImpl.class);
    private List<UserDto> userDtos;

    @Autowired
    public SendingCongratulationsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 59 8 ? * MON-FRI")
    public void findByBirthday() {
        logger.info("Find by birthday");
        userDtos = userService.findByBirthday(LocalDate.now());
    }

    @Override
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 1,
            backoff = @Backoff(delay = 3600000))
    @Scheduled(cron = "0 0 9 ? * MON-FRI")
    public void sendCongratulations() {
        logger.info("Sending congratulations");
        if (userDtos.size() != 0) {
            for (UserDto userDto : userDtos) {
                StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
                StringTemplate mail = group.getInstanceOf("Birthday");
                mail.setAttribute("firstName", userDto.getFirstName());
                mail.setAttribute("lastName", userDto.getLastName());
                SendingService.send(mail, userDto.getEmail());
            }
        }
    }

    @Override
    @Recover
    public void sendSystemAdminNotification(RuntimeException ex) {
        logger.error("Sending System Admin Notification {}",ex);
        List<UserDto> systemAdmins = userService.findAllByRole(Role.SYSTEM_ADMIN);
        if (systemAdmins.size() != 0) {
            for (UserDto userDto : systemAdmins) {
                StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
                StringTemplate mail = group.getInstanceOf("SystemAdminNotification");
                mail.setAttribute("firstName", userDto.getFirstName());
                mail.setAttribute("lastName", userDto.getLastName());
                mail.setAttribute("error", ex.getMessage());
                SendingService.send(mail, userDto.getEmail());
            }
        }
    }
}

