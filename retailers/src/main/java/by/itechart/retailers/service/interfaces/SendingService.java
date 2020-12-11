package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.service.impl.SendingCredentialsServiceImpl;
import org.antlr.stringtemplate.StringTemplate;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public interface SendingService {
    static void send(StringTemplate mail, String email) {
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Retailers");
            message.setText(mail.toString());

            Transport tr = session.getTransport();
            tr.connect(properties.get("mail.smtps.user")
                                 .toString(), properties.get("mail.smtps.password")
                                                        .toString());
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
