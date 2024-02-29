package com.ex.hero.host.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.ex.hero.host.model.Host;
import com.ex.hero.host.vo.HostInfoVo;
import com.ex.hero.host.vo.HostUserVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class HostDetailResponse {

	@Schema(description = "호스트 정보")
	@JsonUnwrapped
	private final HostInfoVo hostInfo;

	@Schema(description = "마스터 유저의 정보")
	private final HostUserVo masterUser;

	@Schema(description = "호스트 유저 정보")
	private final List<HostUserVo> hostUsers;

	public static HostDetailResponse of(Host host, List<HostUserVo> hostUserVoSet) {
		HostDetailResponseBuilder builder = HostDetailResponse.builder();
		List<HostUserVo> hostUserVoList = new ArrayList<>();
		hostUserVoSet.forEach(
			hostUserVo -> {
				if (hostUserVo.getMemberInfoVo().getMemberId().equals(host.getMasterUserId())) {
					builder.masterUser(hostUserVo);
				} else {
					hostUserVoList.add(hostUserVo);
				}
			});

		return builder.hostInfo(HostInfoVo.from(host))
			.hostUsers(hostUserVoList)
			.build();
	}

}
