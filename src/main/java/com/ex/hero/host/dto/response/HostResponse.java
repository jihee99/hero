package com.ex.hero.host.dto.response;

import java.util.UUID;

import com.ex.hero.host.model.Host;
import com.ex.hero.host.vo.HostInfoVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HostResponse {
	@Schema(description = "호스트 프로필")
	@JsonUnwrapped
	private final HostInfoVo profile;

	@Schema(description = "마스터 유저의 고유 아이디")
	private final UUID masterUserId;


	public static HostResponse of(Host host) {
		return HostResponse.builder()
			.profile(HostInfoVo.from(host))
			.masterUserId(host.getMasterUserId())
			.build();
	}
}
