package com.ex.hero.host.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.model.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinHostUseCase {

	private final CommonHostService commonHostService;
	private final HostService hostService;
	private final MemberUtils memberUtils;


	@Transactional
	public HostDetailResponse execute(UUID hostId) {
		final UUID userId = memberUtils.getCurrentMemberId();
		final Host host = commonHostService.findById(hostId);

		return commonHostService.toHostDetailResponseExecute(hostService.activateHostUser(host, userId));
	}

}
