package com.ex.hero.host.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.host.dto.request.UpdateHostRequest;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostProfile;
import com.ex.hero.host.repository.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateHostProfileUseCase {
	private final CommonHostService commonHostService;
	private final HostService hostService;
	private final HostRepository hostRepository;

	@Transactional
	public HostDetailResponse execute(UUID hostId, UpdateHostRequest updateHostRequest) {
		final Host host = commonHostService.findById(hostId);

		return commonHostService.toHostDetailResponseExecute(
			hostService.updateHostProfile(host, toHostprofile(updateHostRequest)));
	}

	public HostProfile toHostprofile(UpdateHostRequest updateHostRequest) {
		return HostProfile.builder()
			.introduce(updateHostRequest.getIntroduce())
			.contactEmail(updateHostRequest.getContactEmail())
			.contactNumber(updateHostRequest.getContactNumber())
			.build();
	}
}
