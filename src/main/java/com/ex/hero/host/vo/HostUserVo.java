package com.ex.hero.host.vo;

import com.ex.hero.host.model.HostRole;
import com.ex.hero.host.model.HostUser;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.vo.MemberInfoVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HostUserVo {
	@JsonUnwrapped
	private final MemberInfoVo memberInfoVo;
	private final HostRole role;
	private final Boolean active;

	public static HostUserVo from(Member member, HostUser hostUser) {
		return HostUserVo.builder()
			.memberInfoVo(member.toUserInfoVo())
			.active(hostUser.getActive())
			.role(hostUser.getRole())
			.build();
	}

	public static HostUserVo from(MemberInfoVo memberInfoVo, HostUser hostUser) {
		return HostUserVo.builder()
			.memberInfoVo(memberInfoVo)
			.active(hostUser.getActive())
			.role(hostUser.getRole())
			.build();
	}

}
