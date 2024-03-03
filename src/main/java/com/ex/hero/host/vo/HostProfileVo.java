package com.ex.hero.host.vo;

import java.util.UUID;

import com.ex.hero.host.model.Host;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class HostProfileVo {

	private final UUID hostId;

	private final String name;

	private final String introduce;

	public static HostProfileVo from(Host host) {
		return HostProfileVo.builder()
			.hostId(host.getId())
			.name(host.getProfile().getName())
			.introduce(host.getProfile().getIntroduce())
			.build();
	}
}
