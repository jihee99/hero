package com.ex.hero.group.service;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;
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
		final Member member = memberUtils.getCurrentMember();
		final Group group = commonGroupService.findById(groupId);

		member.setAccountRole(MemberType.MANAGER);
		return commonGroupService.toGroupDetailResponseExecute(groupService.activateGroupUser(group, userId));
	}

}
