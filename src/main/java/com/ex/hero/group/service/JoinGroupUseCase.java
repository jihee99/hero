package com.ex.hero.group.service;

import java.util.UUID;

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
	public GroupDetailResponse execute(UUID hostId) {
		final UUID userId = memberUtils.getCurrentMemberId();
		final Group group = commonGroupService.findById(hostId);

		return commonGroupService.toHostDetailResponseExecute(groupService.activateGroupUser(group, userId));
	}

}
