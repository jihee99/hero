package com.ex.hero.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final MemberUtils memberUtils;


	@Transactional
	public GroupDetailResponse execute(Long groupId) {
		final Long userId = memberUtils.getCurrentMemberId();
		final Group group = commonGroupService.findById(groupId);

		return commonGroupService.toGroupDetailResponseExecute(groupService.activateGroupUser(group, userId));
	}

}
