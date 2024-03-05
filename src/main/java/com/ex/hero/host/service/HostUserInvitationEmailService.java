package com.ex.hero.host.service;

import static com.ex.hero.common.util.EmailContents.MESSAGE_SUBJECT;
import static com.ex.hero.common.util.EmailContents.SENDER_ADDRESS;
import static com.ex.hero.common.util.EmailContents.MESSAGE_PREFIX;
import static com.ex.hero.common.util.EmailContents.MESSAGE_SUFFIX;

import com.ex.hero.host.model.HostRole;
import com.ex.hero.mail.dto.EmailUserInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostUserInvitationEmailService {

    private final JavaMailSender mailSender;
    private final StringBuilder stringBuilder = new StringBuilder();

    public void execute(EmailUserInfo toEmailUserInfo, String hostName, HostRole role) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, toEmailUserInfo.getEmail());
        message.setFrom(SENDER_ADDRESS);
        message.setSubject(MESSAGE_SUBJECT);
        message.setText(getMessage(), "utf-8", "html");

        mailSender.send(message);
    }


    private String getMessage(){
        stringBuilder.setLength(0);
        return String.valueOf(stringBuilder.append(MESSAGE_PREFIX)
            .append("여기에 코드나 url이 들어가도록!!!")
            .append(MESSAGE_SUFFIX));
    }

}
