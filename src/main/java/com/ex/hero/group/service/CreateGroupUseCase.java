package com.ex.hero.group.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.dto.request.CreateGroupRequest;
import com.ex.hero.group.model.dto.request.response.GroupResponse;
import com.ex.hero.user.model.AccountRole;
import com.ex.hero.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateGroupUseCase {

	private final UserUtils userUtils;
	private final GroupService groupService;
	private final CommonGroupService commonGroupService;

	@Transactional
	public GroupResponse execute(CreateGroupRequest createGroupRequest) {
		// 존재하는 유저인지 검증
		final User user = userUtils.getCurrentMember();
		final Long id = user.getUserId();
		// 호스트 생성
		final Group group = groupService.createGroup( Group.toEntity(createGroupRequest, id));
		// 생성한 유저를 마스터 권한으로 등록
		final GroupUser masterGroupUser = toMasterGroupUser(group.getId(), id);

		// 즉시 활성화
		user.updateAccountRole(AccountRole.MASTER);
		masterGroupUser.activate();
		return GroupResponse.of(groupService.addGroupUser(group, masterGroupUser));
	}


	public GroupUser toMasterGroupUser(Long groupId, Long userId) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MASTER).build();
	}
}
