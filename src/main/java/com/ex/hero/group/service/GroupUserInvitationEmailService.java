package com.ex.hero.group.service;

import static com.ex.hero.common.util.EmailContents.MESSAGE_SUBJECT;
import static com.ex.hero.common.util.EmailContents.SENDER_ADDRESS;
import static com.ex.hero.common.util.EmailContents.MESSAGE_PREFIX;
import static com.ex.hero.common.util.EmailContents.MESSAGE_SUFFIX;

import com.ex.hero.group.model.GroupUserRole;
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
public class GroupUserInvitationEmailService {

    private final JavaMailSender mailSender;
    private final StringBuilder stringBuilder = new StringBuilder();

    public void execute(EmailUserInfo toEmailUserInfo, String hostName, GroupUserRole role) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, toEmailUserInfo.getEmail());
            message.setFrom(SENDER_ADDRESS);
            message.setSubject(MESSAGE_SUBJECT);
            message.setText(getMessage(toEmailUserInfo.getName(), hostName), "utf-8", "html");

            System.out.println(toEmailUserInfo.getName());
            System.out.println(hostName);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String getMessage(String userName, String hostName){
        stringBuilder.setLength(0);
        return String.valueOf(stringBuilder.append(MESSAGE_PREFIX)
            .append(userName + " 님, 티켓히어로 "+hostName + " 그룹 초대 알림입니다.")
            .append(MESSAGE_SUFFIX));
    }

}
