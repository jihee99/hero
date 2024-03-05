package com.ex.hero.host.service;

import java.util.*;
import java.util.stream.Collectors;

import com.ex.hero.host.model.HostRole;
import org.springframework.stereotype.Service;

import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.exception.HostNotFoundException;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostUser;
import com.ex.hero.host.repository.HostRepository;
import com.ex.hero.host.vo.HostUserVo;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.member.vo.MemberInfoVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonHostService {

	private final MemberRepository memberRepository;
	private final HostRepository hostRepository;

	public Host findById(UUID hostId) {
		return hostRepository.findById(hostId).orElseThrow(() -> HostNotFoundException.EXCEPTION);
	}

	public HostUser toHostUser(UUID hostId, UUID userId, HostRole role) {
		final Host host = hostRepository.findById(hostId).orElseThrow(() -> HostNotFoundException.EXCEPTION);
		return HostUser.builder().userId(userId).host(host).role(role).build();
	}

	public HostDetailResponse toHostDetailResponseExecute(Host host) {
		final List<UUID> userIds = host.getHostUser_UserIds();
		final List<Member> userList = memberRepository.findAllByUserIdIn(userIds);
		final Map<UUID, Member> userMap =
			userList.stream().collect(Collectors.toMap(Member::getUserId, user -> user));
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
