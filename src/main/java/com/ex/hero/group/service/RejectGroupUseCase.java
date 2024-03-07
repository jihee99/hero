package com.ex.hero.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RejectGroupUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final MemberUtils memberUtils;

	public GroupDetailResponse execute(UUID groupId) {
		final UUID userId = memberUtils.getCurrentMemberId();
		final Group group = commonGroupService.findById(groupId);

		return commonGroupService.toGroupDetailResponseExecute(groupService.removeGroupUser(group, userId));
	}
}
