package com.ex.hero.group.model;

import com.ex.hero.common.vo.GroupInfoVo;
import com.ex.hero.common.vo.GroupProfileVo;
import com.ex.hero.group.exception.*;
import com.ex.hero.group.model.dto.request.CreateGroupRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Embedded private GroupProfile profile;

    private Long masterUserId;

    // 단방향 oneToMany 매핑
    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private final Set<GroupUser> groupUsers = new HashSet<>();

    public void addGroupUsers(Set<GroupUser> groupUserList) {
        groupUserList.forEach(this::validateGroupUserExistence);
        this.groupUsers.addAll(groupUserList);
    }

    public void inviteGroupUsers(Set<GroupUser> groupUserList) {
        groupUserList.forEach(this::validateGroupUserExistence);
        this.groupUsers.addAll(groupUserList);
    }

    public Boolean hasGroupUserId(Long userId) {
        return this.groupUsers.stream().anyMatch(groupUser -> groupUser.getUserId().equals(userId));
    }

    public Boolean hasGroupUser(GroupUser groupUser) {
        return this.hasGroupUserId(groupUser.getUserId());
    }

    public GroupUser getGroupUserByUserId(Long userId) {
        return this.groupUsers.stream()
            .filter(groupUser -> groupUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> GroupUserNotFoundException.EXCEPTION);
    }

    public List<Long> getGroupUser_UserIds() {
        return this.groupUsers.stream().map(GroupUser::getUserId).collect(Collectors.toList());
    }

    public void updateProfile(GroupProfile groupProfile) {
        this.profile.updateProfile(groupProfile);
    }

    public Boolean isManagerGroupUserId(Long userId) {
        return this.groupUsers.stream()
            .anyMatch(
                groupUser ->
                    groupUser.getUserId().equals(userId)
                        && groupUser.getRole().equals(GroupUserRole.MANAGER));
    }

    public Boolean isActiveGroupUserId(Long userId) {
        return this.groupUsers.stream()
            .anyMatch(groupUser -> groupUser.getUserId().equals(userId) && groupUser.getActive());
    }

    public void setGroupUserRole(Long userId, GroupUserRole role) {
        // 마스터의 역할은 수정할 수 없음
        if (this.getMasterUserId().equals(userId))
            throw CannotModifyMasterGroupUserRoleException.EXCEPTION;
        this.groupUsers.stream()
            .filter(groupUser -> groupUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> GroupUserNotFoundException.EXCEPTION)
            .setGroupUserRole(role);
    }

    public void removeGroupUser(Long userId) {
        if (this.isActiveGroupUserId(userId)) throw AlreadyJoinedGroupException.EXCEPTION;
        this.groupUsers.remove(this.getGroupUserByUserId(userId));
    }

    /** 해당 유저가 그룹에 이미 속하는지 확인하는 검증 로직] */
    public void validateGroupUserIdExistence(Long userId) {
        if (this.hasGroupUserId(userId)) {
            throw AlreadyJoinedGroupException.EXCEPTION;
        }
    }

    public void validateGroupUserExistence(GroupUser groupUser) {
        validateGroupUserIdExistence(groupUser.getUserId());
    }

    /** 해당 유저가 그룹에 속하는지 확인하는 검증 로직 */
    public void validateGroupUser(Long userId) {
        if (!this.hasGroupUserId(userId)) throw ForbiddenGroupException.EXCEPTION;
    }

    /** 해당 유저가 그룹에 속하며 가입 승인을 완료했는지 (활성상태) 확인하는 검증 로직 */
    public void validateActiveGroupUser(Long userId) {
        this.validateGroupUser(userId);
        if (!this.isActiveGroupUserId(userId)) throw NotAcceptedGroupException.EXCEPTION;
    }

    /** 해당 유저가 매니저 이상인지 확인하는 검증 로직 */
    public void validateManagerGroupUser(Long userId) {
        this.validateActiveGroupUser(userId);
        if (!this.isManagerGroupUserId(userId) && !this.getMasterUserId().equals(userId)) {
            throw NotManagerGroupException.EXCEPTION;
        }
    }

    /** 해당 유저가 그룹의 마스터(담당자, 방장)인지 확인하는 검증 로직 */
    public void validateMasterGroupUser(Long groupId) {
        this.validateActiveGroupUser(groupId);
        if (!this.getMasterUserId().equals(groupId)) throw NotMasterGroupException.EXCEPTION;
    }


    public GroupInfoVo toGroupInfoVo() {
        return GroupInfoVo.from(this);
    }

    public GroupProfileVo toGroupProfileVo() {
        return GroupProfileVo.from(this);
    }


    public static Group toEntity(CreateGroupRequest createGroupRequest, Long masterUserId) {
        return Group.builder()
            .name(createGroupRequest.name())
            .contactEmail(createGroupRequest.contactEmail())
            .contactNumber(createGroupRequest.contactNumber())
            .masterUserId(masterUserId)
            .build();
    }


    @Builder
    public Group(
        String name,
        String introduce,
        String contactEmail,
        String contactNumber,
        Long masterUserId) {
        this.profile =
            GroupProfile.builder()
                .name(name)
                .introduce(introduce)
                .contactEmail(contactEmail)
                .contactNumber(contactNumber)
                .build();
        this.masterUserId = masterUserId;

    }


}
