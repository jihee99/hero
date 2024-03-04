package com.ex.hero.common.handler;

import java.util.UUID;

import com.ex.hero.host.service.HostUserInvitationEmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.ex.hero.events.host.HostUserInvitationEvent;
import com.ex.hero.host.model.HostRole;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HostUserInvitationEventHandler {
    private final CommonMemberService commonMemberService;

    private final HostUserInvitationEmailService invitationEmailService;

    @Async
    @TransactionalEventListener(
            classes = HostUserInvitationEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    public void handle(HostUserInvitationEvent hostUserInvitationEvent) {
        final UUID userId = hostUserInvitationEvent.getUserId();
        final Member member = commonMemberService.queryMember(userId);
        final HostRole role = hostUserInvitationEvent.getRole();
        final String hostName = hostUserInvitationEvent.getHostProfileVo().getName();

        System.out.println("###mail handler");
        System.out.println(member.getEmail());
        System.out.println(role.getName());
        System.out.println(hostName);

        invitationEmailService.execute(member.toEmailUserInfo(), hostName, role);
    }
}
