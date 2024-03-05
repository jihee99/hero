package com.ex.hero.host.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ex.hero.host.exception.MessagingServerException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import com.ex.hero.events.host.HostUserInvitationEvent;
import com.ex.hero.host.dto.request.InviteHostRequest;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.exception.HostNotFoundException;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostRole;
import com.ex.hero.host.model.HostUser;
import com.ex.hero.host.repository.HostRepository;
import com.ex.hero.host.vo.HostUserVo;
import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.member.vo.MemberInfoVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteHostUseCase {

	private final CommonHostService commonHostService;
	private final HostService hostService;
	private final MemberRepository memberRepository;
	private final HostUserInvitationEmailService invitationEmailService;

	public HostDetailResponse execute(UUID hostId, InviteHostRequest inviteHostRequest) {
		final Host host = commonHostService.findById(hostId);
		final Member inviteMember = memberRepository.findByEmailAndStatus(inviteHostRequest.getEmail(), Boolean.TRUE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
		final UUID invitedUserId = inviteMember.getUserId();
		final HostRole role = inviteHostRequest.getRole();

		final HostUser hostUser = HostUser.builder().userId(invitedUserId).role(role).build();

		HostDetailResponse hostDetailResponse = commonHostService.toHostDetailResponseExecute(hostService.inviteHostUser(host, hostUser));

		try {
			invitationEmailService.execute(inviteMember.toEmailUserInfo(), host.toHostProfileVo().getName(), role);
		} catch (MessagingException e) {
			throw MessagingServerException.EXCEPTION;
		}

		return hostDetailResponse;
	}


}
