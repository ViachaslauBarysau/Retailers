package by.itechart.retailers.service.interfaces;

import org.antlr.stringtemplate.StringTemplate;

public interface SendingMailService {
    /*static void send(StringTemplate mail, String email) {
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
    }*/

    void sendMail(StringTemplate mail, String emailAddress);
}
