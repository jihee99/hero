package com.ex.hero.group.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import com.ex.hero.user.model.AccountRole;
import com.ex.hero.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final UserUtils userUtils;


	@Transactional
	public GroupDetailResponse execute(Long groupId) {
		final Long userId = userUtils.getCurrentMemberId();
		final User user = userUtils.getCurrentMember();
		final Group group = commonGroupService.findById(groupId);

		user.updateAccountRole(AccountRole.MANAGER);
		return commonGroupService.toGroupDetailResponseExecute(groupService.activateGroupUser(group, userId));
	}

}
