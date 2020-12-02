package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.service.interfaces.SendingCredentialsService;
import by.itechart.retailers.service.interfaces.SendingService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendingCredentialsServiceImpl implements SendingCredentialsService {
    Logger logger = LoggerFactory.getLogger(SendingCredentialsServiceImpl.class);
    @Override
    public void send(UserDto userDto) {
        logger.info("Send");
        StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
        StringTemplate mail = group.getInstanceOf("Credentials");
        mail.setAttribute("name", userDto.getCustomer()
                                         .getName());
        mail.setAttribute("email", userDto.getEmail());
        mail.setAttribute("password", userDto.getPassword());

        SendingService.send(mail, userDto.getEmail());

    }
}
