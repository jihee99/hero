package com.ex.hero.group.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.group.exception.GroupNotFoundException;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupProfile;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.group.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepository;

	public Group createGroup(Group group) {
		return groupRepository.save(group);
	}

	public Group addGroupUser(Group group, GroupUser groupUser) {
		group.addGroupUsers(Set.of(groupUser));
		return groupRepository.save(group);
	}

	public Group inviteGroupUser(Group group, GroupUser groupUser) {
		group.inviteGroupUsers(Set.of(groupUser));
		return groupRepository.save(group);
	}

	public Group updateGroupUserRole(Group group, Long userId, GroupUserRole role) {
		group.setGroupUserRole(userId, role);
		return groupRepository.save(group);
	}

	public Group updateGroupProfile(Group group, GroupProfile profile) {
		group.updateProfile(profile);
		return groupRepository.save(group);
	}

	public Group activateGroupUser(Group group, Long userId) {
		group.getGroupUserByUserId(userId).activate();
		return groupRepository.save(group);
	}

	public Group removeGroupUser(Group group, Long userId) {
		group.removeGroupUser(userId);
		return groupRepository.save(group);
	}

	/* 본인이 속한 그룹 리스트를 가져오는 쿼리(승인완료된 그룹만) */
	public List<Group> findAllByGroupUsers_UserId(Long userId){
		return groupRepository.findAllByGroupUsers_UserIdAndGroupUsers_ActiveTrue(userId);
		// return groupRepository.findAllByGroupUsers_UserId(userId);
	}

	/* 본인이 마스터인 그룹을 가져오는 쿼리 */
	public List<Group> findAllByMasterUserId(Long userId) {
		return groupRepository.findAllByMasterUserId(userId);
	}

	/* 해당 유저가 그룹 사용자에 속하는지 확인하는 검증 로직 */
	public void validateGroupUser(Group group, Long userId) {
		group.validateGroupUser(userId);
	}

	/* 해당 유저가 그룹의 마스터(담당자, 방장)인지 확인하는 검증 로직 */
	public void validateMasterGroupUser(Group group, Long userId) {
		group.validateMasterGroupUser(userId);
	}

	/* 해당 유저가 슈퍼 호스트인지 확인하는 검증 로직 */
	public void validateManagerGroupUser(Long groupId, Long userId) {
		Group group = groupRepository.findById(groupId).orElseThrow(() -> GroupNotFoundException.EXCEPTION);
		group.validateManagerGroupUser(userId);
	}

}
