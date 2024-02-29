package com.ex.hero.host.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

	private final HostService hostService;
	private final MemberRepository memberRepository;
	private final HostRepository hostRepository;

	public HostDetailResponse execute(UUID hostId, InviteHostRequest inviteHostRequest) {
		final Host host = hostRepository.findById(hostId).orElseThrow(() -> HostNotFoundException.EXCEPTION);
		final Member inviteMember = memberRepository.findByEmailAndStatus(inviteHostRequest.getEmail(), Boolean.TRUE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
		final UUID invitedUserId = inviteMember.getId();
		final HostRole role = inviteHostRequest.getRole();

		final HostUser hostUser = HostUser.builder().userId(invitedUserId).role(role).build();

		return toHostDetailResponseExecute(hostService.inviteHostUser(host, hostUser));
	}


	private HostDetailResponse toHostDetailResponseExecute(Host host) {
		final List<UUID> userIds = host.getHostUser_UserIds();
		final List<Member> userList = memberRepository.findAllByIdIn(userIds);
		final Map<UUID, Member> userMap =
			userList.stream().collect(Collectors.toMap(Member::getId, user -> user));
		final List<HostUserVo> hostUserVoList = new ArrayList<>();

		for (UUID userId : userIds) {
			final Member user = userMap.get(userId);
			if (user != null) {
				final MemberInfoVo memberInfoVo = user.toUserInfoVo();
				final HostUser hostUser = host.getHostUserByUserId(userId);
				final HostUserVo hostUserVo = HostUserVo.from(memberInfoVo, hostUser);
				hostUserVoList.add(hostUserVo);
			}
		}

		return HostDetailResponse.of(host, hostUserVoList);
	}
}
