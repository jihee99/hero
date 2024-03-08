package com.ex.hero.group.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.ex.hero.group.model.Group;
import com.ex.hero.domains.common.vo.GroupInfoVo;
import com.ex.hero.domains.common.vo.GroupUserVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDetailResponse {

	@Schema(description = "그룹 정보")
	@JsonUnwrapped
	private final GroupInfoVo groupInfo;

	@Schema(description = "마스터 유저의 정보")
	private final GroupUserVo masterUser;

	@Schema(description = "그룹 유저 정보")
	private final List<GroupUserVo> groupUsers;

	public static GroupDetailResponse of(Group group, List<GroupUserVo> groupUserVoSet) {
		GroupDetailResponseBuilder builder = GroupDetailResponse.builder();
		List<GroupUserVo> groupUserVoList = new ArrayList<>();
		groupUserVoSet.forEach(
				groupUserVo -> {
				if (groupUserVo.getMemberInfoVo().getMemberId().equals(group.getMasterUserId())) {
					builder.masterUser(groupUserVo);
				} else {
					groupUserVoList.add(groupUserVo);
				}
			});

		return builder.groupInfo(GroupInfoVo.from(group))
			.groupUsers(groupUserVoList)
			.build();
	}

}
