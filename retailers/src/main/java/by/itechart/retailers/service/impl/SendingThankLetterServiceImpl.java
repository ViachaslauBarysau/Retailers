package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.service.interfaces.SendingService;
import by.itechart.retailers.service.interfaces.SendingThankLetterService;
import by.itechart.retailers.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class SendingThankLetterServiceImpl implements SendingThankLetterService {
    private final BillRepository billRepository;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(SendingThankLetterServiceImpl.class);

    @Autowired
    public SendingThankLetterServiceImpl(BillRepository billRepository,
                                         UserService userService) {
        this.billRepository = billRepository;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 17 28-31 * ?")
    public void monthEndSchedule() {
        logger.info("Check for the end of month");
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            sendThankLetter();
        }
    }

    private void sendThankLetter() {
        logger.info("Send thank letters");
        List<Long> userIds = billRepository.findAllBestEmployeeIds();
        System.out.println(userIds);
        List<UserDto> userDtos = userService.findAllById(userIds);
        if (userDtos.size() != 0) {
            for (UserDto userDto : userDtos) {
                StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
                StringTemplate mail = group.getInstanceOf("ThankLetter");
                mail.setAttribute("firstName", userDto.getFirstName());
                mail.setAttribute("lastName", userDto.getLastName());
                SendingService.send(mail, userDto.getEmail());
            }
        }
    }
}
