package com.ex.hero.host.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.MemberUtils;
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
public class JoinHostUseCase {

	private final CommonHostService commonHostService;
	private final HostService hostService;
	private final HostRepository hostRepository;
	private final MemberUtils memberUtils;
	private final MemberRepository memberRepository;

	@Transactional
	public HostDetailResponse execute(UUID hostId) {
		final UUID userId = memberUtils.getCurrentMemberId();
		final Host host = hostRepository.findById(hostId).orElseThrow(() -> HostNotFoundException.EXCEPTION);

		return commonHostService.toHostDetailResponseExecute(hostService.activateHostUser(host, userId));
	}

}
