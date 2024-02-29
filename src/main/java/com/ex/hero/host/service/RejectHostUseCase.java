package com.ex.hero.host.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ex.hero.common.MemberUtils;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.repository.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RejectHostUseCase {
	private final CommonHostService commonHostService;
	private final HostService hostService;
	private final MemberUtils memberUtils;

	public HostDetailResponse execute(UUID hostId) {
		final UUID userId = memberUtils.getCurrentMemberId();
		final Host host = commonHostService.findById(hostId);

		return commonHostService.toHostDetailResponseExecute(hostService.removeHostUser(host, userId));
	}
}
