package com.ex.hero.host.service;

import java.util.Set;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.host.exception.HostNotFoundException;
import com.ex.hero.host.model.Host;
import com.ex.hero.host.model.HostProfile;
import com.ex.hero.host.model.HostRole;
import com.ex.hero.host.model.HostUser;
import com.ex.hero.host.repository.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostService {

	private final HostRepository hostRepository;

	public Host createHost(Host host) {
		return hostRepository.save(host);
	}

	public Host addHostUser(Host host, HostUser hostUser) {
		host.addHostUsers(Set.of(hostUser));
		return hostRepository.save(host);
	}

	public Host inviteHostUser(Host host, HostUser hostUser) {
		System.out.println("invitehostuser 서비스. 여기까지 와야지 디비에 들어간다. 근데 안들어감. ");
		host.inviteHostUsers(Set.of(hostUser));
		log.info("##########1 {}", host.getHostUsers().toString());
		log.info("##########2 {}", host);
		return hostRepository.save(host);
	}

	public Host updateHostUserRole(Host host, UUID userId, HostRole role) {
		host.setHostUserRole(userId, role);
		return hostRepository.save(host);
	}

	public Host updateHostProfile(Host host, HostProfile profile) {
		host.updateProfile(profile);
		return hostRepository.save(host);
	}


	public Host activateHostUser(Host host, UUID userId) {
		host.getHostUserByUserId(userId).activate();
		return hostRepository.save(host);
	}

	public Host removeHostUser(Host host, UUID userId) {
		host.removeHostUser(userId);
		return hostRepository.save(host);
	}

	/** 해당 유저가 호스트에 속하는지 확인하는 검증 로직 */
	public void validateHostUser(Host host, UUID userId) {
		host.validateHostUser(userId);
	}

	/** 해당 유저가 호스트의 마스터(담당자, 방장)인지 확인하는 검증 로직 */
	public void validateMasterHostUser(Host host, UUID userId) {
		host.validateMasterHostUser(userId);
	}

	/** 해당 유저가 슈퍼 호스트인지 확인하는 검증 로직 */
	public void validateManagerHostUser(UUID hostId, UUID userId) {
		Host host = hostRepository.findById(hostId).orElseThrow(() -> HostNotFoundException.EXCEPTION);
		host.validateManagerHostUser(userId);
	}

}
