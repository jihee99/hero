package com.ex.hero.group.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectGroupUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final UserUtils userUtils;

	public GroupDetailResponse execute(Long groupId) {
		final Long userId = userUtils.getCurrentMemberId();
		final Group group = commonGroupService.findById(groupId);

		return commonGroupService.toGroupDetailResponseExecute(groupService.removeGroupUser(group, userId));
	}
}
