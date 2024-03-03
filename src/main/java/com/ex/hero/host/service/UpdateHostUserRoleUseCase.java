package com.ex.hero.host.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ex.hero.host.dto.request.UpdateHostUserRoleRequest;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostRole;
import com.ex.hero.host.repository.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateHostUserRoleUseCase {
	private final CommonHostService commonHostService;
	private final HostService hostService;

	public HostDetailResponse execute(UUID hostId, UpdateHostUserRoleRequest updateHostUserRoleRequest) {
		final Host host = commonHostService.findById(hostId);
		final UUID updateUserId = updateHostUserRoleRequest.getUserId();

		final HostRole updateUserRole = updateHostUserRoleRequest.getRole();
		return commonHostService.toHostDetailResponseExecute(hostService.updateHostUserRole(host, updateUserId, updateUserRole));
	}

}
