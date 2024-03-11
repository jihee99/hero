package com.ex.hero.common.vo;

import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.member.model.Member;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupUserVo {
	@JsonUnwrapped
	private final MemberInfoVo memberInfoVo;
	private final GroupUserRole role;
	private final Boolean active;

	public static GroupUserVo from(Member member, GroupUser groupUser) {
		return GroupUserVo.builder()
			.memberInfoVo(member.toMemberInfoVo())
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

	public static GroupUserVo from(MemberInfoVo memberInfoVo, GroupUser groupUser) {
		return GroupUserVo.builder()
			.memberInfoVo(memberInfoVo)
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

}
