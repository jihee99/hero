package com.ex.hero.group.model;

import com.ex.hero.group.dto.request.CreateGroupRequest;
import com.ex.hero.group.exception.*;
import com.ex.hero.group.vo.GroupInfoVo;
import com.ex.hero.group.vo.GroupProfileVo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id")
    private UUID id;

    @Embedded private GroupProfile profile;

    private UUID masterUserId;

    // 단방향 oneToMany 매핑
    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private final Set<GroupUser> groupUsers = new HashSet<>();

    public void addHostUsers(Set<GroupUser> groupUserList) {
        groupUserList.forEach(this::validateGroupUserExistence);
        this.groupUsers.addAll(groupUserList);
    }

    public void inviteGroupUsers(Set<GroupUser> groupUserList) {
        groupUserList.forEach(this::validateGroupUserExistence);
        this.groupUsers.addAll(groupUserList);
//        hostUserList.forEach(hostUser -> Events.raise(HostUserInvitationEvent.of(this, hostUser)));
    }

    public Boolean hasHostUserId(UUID userId) {
        return this.groupUsers.stream().anyMatch(hostUser -> hostUser.getUserId().equals(userId));
    }

    public Boolean hasGroupUser(GroupUser groupUser) {
        return this.hasHostUserId(groupUser.getUserId());
    }

    public GroupUser getGroupUserByUserId(UUID userId) {
        return this.groupUsers.stream()
            .filter(hostUser -> hostUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> GroupUserNotFoundException.EXCEPTION);
    }

    public List<UUID> getHostUser_UserIds() {
        return this.groupUsers.stream().map(GroupUser::getUserId).collect(Collectors.toList());
    }

    public void updateProfile(GroupProfile groupProfile) {
        this.profile.updateProfile(groupProfile);
    }

    public Boolean isManagerGroupUserId(UUID userId) {
        return this.groupUsers.stream()
            .anyMatch(
                hostUser ->
                    hostUser.getUserId().equals(userId)
                        && hostUser.getRole().equals(GroupUserRole.MANAGER));
    }

    public Boolean isActiveGroupUserId(UUID userId) {
        return this.groupUsers.stream()
            .anyMatch(hostUser -> hostUser.getUserId().equals(userId) && hostUser.getActive());
    }

    public void setGroupUserRole(UUID userId, GroupUserRole role) {
        // 마스터의 역할은 수정할 수 없음
        if (this.getMasterUserId().equals(userId))
            throw CannotModifyMasterGroupUserRoleException.EXCEPTION;
        this.groupUsers.stream()
            .filter(hostUser -> hostUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> GroupUserNotFoundException.EXCEPTION)
            .setGroupUserRole(role);
    }

    public void removeGroupUser(UUID userId) {
        if (this.isActiveGroupUserId(userId)) throw AlreadyJoinedGroupException.EXCEPTION;
        this.groupUsers.remove(this.getGroupUserByUserId(userId));
    }

    /** 해당 유저가 호스트에 이미 속하는지 확인하는 검증 로직] */
    public void validateGroupUserIdExistence(UUID userId) {
        if (this.hasHostUserId(userId)) {
            throw AlreadyJoinedGroupException.EXCEPTION;
        }
    }

    public void validateGroupUserExistence(GroupUser groupUser) {
        validateGroupUserIdExistence(groupUser.getUserId());
    }

    /** 해당 유저가 호스트에 속하는지 확인하는 검증 로직 */
    public void validateGroupUser(UUID userId) {
        if (!this.hasHostUserId(userId)) throw ForbiddenGroupException.EXCEPTION;
    }

    /** 해당 유저가 호스트에 속하며 가입 승인을 완료했는지 (활성상태) 확인하는 검증 로직 */
    public void validateActiveGroupUser(UUID userId) {
        this.validateGroupUser(userId);
        if (!this.isActiveGroupUserId(userId)) throw NotAcceptedGroupException.EXCEPTION;
    }

    /** 해당 유저가 매니저 이상인지 확인하는 검증 로직 */
    public void validateManagerHostUser(UUID userId) {
        this.validateActiveGroupUser(userId);
        if (!this.isManagerGroupUserId(userId) && !this.getMasterUserId().equals(userId)) {
            throw NotManagerGroupException.EXCEPTION;
        }
    }

    /** 해당 유저가 호스트의 마스터(담당자, 방장)인지 확인하는 검증 로직 */
    public void validateMasterGroupUser(UUID userId) {
        this.validateActiveGroupUser(userId);
        if (!this.getMasterUserId().equals(userId)) throw NotMasterGroupException.EXCEPTION;
    }


    public GroupInfoVo toHostInfoVo() {
        return GroupInfoVo.from(this);
    }

    public GroupProfileVo toHostProfileVo() {
        return GroupProfileVo.from(this);
    }


    public static Group toEntity(CreateGroupRequest createGroupRequest, UUID masterUserId) {
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
        UUID masterUserId) {
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