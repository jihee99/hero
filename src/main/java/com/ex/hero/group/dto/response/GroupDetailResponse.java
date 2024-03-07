package com.ex.hero.group.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.ex.hero.group.model.Group;
import com.ex.hero.group.vo.GroupInfoVo;
import com.ex.hero.group.vo.GroupUserVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDetailResponse {

	@Schema(description = "호스트 정보")
	@JsonUnwrapped
	private final GroupInfoVo hostInfo;

	@Schema(description = "마스터 유저의 정보")
	private final GroupUserVo masterUser;

	@Schema(description = "호스트 유저 정보")
	private final List<GroupUserVo> hostUsers;

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

		return builder.hostInfo(GroupInfoVo.from(group))
			.hostUsers(groupUserVoList)
			.build();
	}

}
