package com.ex.hero.host.vo;

import java.util.UUID;

import com.ex.hero.host.model.Host;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class HostInfoVo {
	private final UUID hostId;

	private final String name;

	private final String introduce;


	private final String contactEmail;

	private final String contactNumber;

	private final Boolean partner;

	public static HostInfoVo from(Host host) {
		return HostInfoVo.builder()
			.hostId(host.getId())
			.name(host.getProfile().getName())
			.introduce(host.getProfile().getIntroduce())
			.contactEmail(host.getProfile().getContactEmail())
			.contactNumber(host.getProfile().getContactNumber())
			.build();
	}
}
