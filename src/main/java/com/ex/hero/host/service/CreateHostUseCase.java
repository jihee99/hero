package com.ex.hero.host.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.host.dto.request.CreateHostRequest;
import com.ex.hero.host.dto.response.HostResponse;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostRole;
import com.ex.hero.host.model.HostUser;
import com.ex.hero.member.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateHostUseCase {

	private final MemberUtils memberUtils;
	private final HostService hostService;
	private final CommonHostService commonHostService;

	@Transactional
	public HostResponse execute(CreateHostRequest createHostRequest) {
		// 존재하는 유저인지 검증
		final Member member = memberUtils.getCurrentMember();
		final UUID id = member.getId();
		// 호스트 생성
		final Host host = hostService.createHost( Host.toEntity(createHostRequest, id));
		// 생성한 유저를 마스터 권한으로 등록
		final HostUser masterHostUser = toMasterHostUser(host.getId(), id);

		// 즉시 활성화
		masterHostUser.activate();
		return HostResponse.of(hostService.addHostUser(host, masterHostUser));
	}

	public HostUser toMasterHostUser(UUID hostId, UUID userId) {
		final Host host = commonHostService.findById(hostId);
		return HostUser.builder().userId(userId).host(host).role(HostRole.MASTER).build();
	}

}
