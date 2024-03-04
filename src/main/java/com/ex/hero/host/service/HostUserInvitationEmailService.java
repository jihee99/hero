package com.ex.hero.host.service;

import com.ex.hero.host.model.HostRole;
import com.ex.hero.mail.dto.EmailUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostUserInvitationEmailService {

    private final JavaMailSender javaMailSender;

    public void execute(EmailUserInfo toEmailUserInfo, String hostName, HostRole role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("oosoojh31@gmail.com");
        message.setFrom("oosoojh31@gmail.com");
        message.setSubject("localhost:8080 email 확인");
        message.setText("Test");

        javaMailSender.send(message);
    }

//    public void execute(EmailUserInfo userInfo, String hostName, HostRole hostRole) {
//        Context context = new Context();
//        context.setVariable("userInfo", userInfo);
//        context.setVariable("hostName", hostName);
//        context.setVariable("role", hostRole.getValue());
//        awsSesUtils.singleEmailRequest(
//                userInfo, "테스트" + hostName + " 호스트 초대 알림 드립니다.", "hostInvite", context);
//    }
}
