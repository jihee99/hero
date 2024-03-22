package com.ex.hero.common.vo;

import com.ex.hero.group.model.GroupUser;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.user.model.User;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupUserVo {
	@JsonUnwrapped
	private final UserInfoVo userInfoVo;
	private final GroupUserRole role;
	private final Boolean active;

	public static GroupUserVo from(User user, GroupUser groupUser) {
		return GroupUserVo.builder()
			.userInfoVo(user.toUserInfoVo())
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

	public static GroupUserVo from(UserInfoVo userInfoVo, GroupUser groupUser) {
		return GroupUserVo.builder()
			.userInfoVo(userInfoVo)
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

}
