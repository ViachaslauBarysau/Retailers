package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.service.interfaces.SendingCredentialsService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Service
public class SendingCredentialsServiceImpl implements SendingCredentialsService {

    @Override
    public void send(UserDto userDto) {
        StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
        StringTemplate mail = group.getInstanceOf("Credentials");
        mail.setAttribute("name", userDto.getCustomer()
                                         .getName());
        mail.setAttribute("email", userDto.getEmail());
        mail.setAttribute("password", userDto.getPassword());

        try {
            Properties properties = new Properties();

            properties.load(SendingCredentialsServiceImpl.class.getClassLoader()
                                                               .getResourceAsStream("mail.properties"));

            mail.setAttribute("retailersEmail", properties.get("mail.smtps.user")
                                                          .toString());
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.get("mail.smtps.user")
                                                          .toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userDto.getEmail()));
            message.setSubject("Retailers Credentials");
            message.setText(mail.toString());

            Transport tr = session.getTransport();
            tr.connect(properties.get("mail.smtps.user")
                                 .toString(), properties.get("mail.smtps.password")
                                                        .toString());
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
        } catch (IOException | MessagingException e) {
            log.error(e.getMessage());
        }

    }
}
