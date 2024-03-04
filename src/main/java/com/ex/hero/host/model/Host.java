package com.ex.hero.host.model;

import com.ex.hero.common.aop.domainEvent.Events;
import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.events.host.HostUserInvitationEvent;
import com.ex.hero.host.dto.request.CreateHostRequest;
import com.ex.hero.host.exception.*;
import com.ex.hero.host.vo.HostInfoVo;
import com.ex.hero.host.vo.HostProfileVo;

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
@Entity(name = "host")
public class Host{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "host_id")
    private UUID id;

    @Embedded private HostProfile profile;

    private UUID masterUserId;

    @OneToMany(
            mappedBy = "host",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private final Set<HostUser> hostUsers = new HashSet<>();

    public void addHostUsers(Set<HostUser> hostUserList) {
        hostUserList.forEach(this::validateHostUserExistence);
        this.hostUsers.addAll(hostUserList);
    }

    public void inviteHostUsers(Set<HostUser> hostUserList) {
        hostUserList.forEach(this::validateHostUserExistence);
        this.hostUsers.addAll(hostUserList);
        hostUserList.forEach(hostUser -> Events.raise(HostUserInvitationEvent.of(this, hostUser)));

    }

    public Boolean hasHostUserId(UUID userId) {
        return this.hostUsers.stream().anyMatch(hostUser -> hostUser.getUserId().equals(userId));
    }

    public Boolean hasHostUser(HostUser hostUser) {
        return this.hasHostUserId(hostUser.getUserId());
    }

    public HostUser getHostUserByUserId(UUID userId) {
        return this.hostUsers.stream()
            .filter(hostUser -> hostUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> HostUserNotFoundException.EXCEPTION);
    }

    public List<UUID> getHostUser_UserIds() {
        return this.hostUsers.stream().map(HostUser::getUserId).collect(Collectors.toList());
    }

    public void updateProfile(HostProfile hostProfile) {
        this.profile.updateProfile(hostProfile);
    }

    public Boolean isManagerHostUserId(UUID userId) {
        return this.hostUsers.stream()
            .anyMatch(
                hostUser ->
                    hostUser.getUserId().equals(userId)
                        && hostUser.getRole().equals(HostRole.MANAGER));
    }

    public Boolean isActiveHostUserId(UUID userId) {
        return this.hostUsers.stream()
            .anyMatch(hostUser -> hostUser.getUserId().equals(userId) && hostUser.getActive());
    }

    public void setHostUserRole(UUID userId, HostRole role) {
        // 마스터의 역할은 수정할 수 없음
        if (this.getMasterUserId().equals(userId))
            throw CannotModifyMasterHostRoleException.EXCEPTION;
        this.hostUsers.stream()
            .filter(hostUser -> hostUser.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> HostUserNotFoundException.EXCEPTION)
            .setHostRole(role);
    }

    public void removeHostUser(UUID userId) {
        if (this.isActiveHostUserId(userId)) throw AlreadyJoinedHostException.EXCEPTION;
        this.hostUsers.remove(this.getHostUserByUserId(userId));
    }

    /** 해당 유저가 호스트에 이미 속하는지 확인하는 검증 로직] */
    public void validateHostUserIdExistence(UUID userId) {
        if (this.hasHostUserId(userId)) {
            throw AlreadyJoinedHostException.EXCEPTION;
        }
    }

    public void validateHostUserExistence(HostUser hostUser) {
        validateHostUserIdExistence(hostUser.getUserId());
    }

    /** 해당 유저가 호스트에 속하는지 확인하는 검증 로직 */
    public void validateHostUser(UUID userId) {
        if (!this.hasHostUserId(userId)) throw ForbiddenHostException.EXCEPTION;
    }

    /** 해당 유저가 호스트에 속하며 가입 승인을 완료했는지 (활성상태) 확인하는 검증 로직 */
    public void validateActiveHostUser(UUID userId) {
        this.validateHostUser(userId);
        if (!this.isActiveHostUserId(userId)) throw NotAcceptedHostException.EXCEPTION;
    }

    /** 해당 유저가 매니저 이상인지 확인하는 검증 로직 */
    public void validateManagerHostUser(UUID userId) {
        this.validateActiveHostUser(userId);
        if (!this.isManagerHostUserId(userId) && !this.getMasterUserId().equals(userId)) {
            throw NotManagerHostException.EXCEPTION;
        }
    }

    /** 해당 유저가 호스트의 마스터(담당자, 방장)인지 확인하는 검증 로직 */
    public void validateMasterHostUser(UUID userId) {
        this.validateActiveHostUser(userId);
        if (!this.getMasterUserId().equals(userId)) throw NotMasterHostException.EXCEPTION;
    }


    public HostInfoVo toHostInfoVo() {
        return HostInfoVo.from(this);
    }

    public HostProfileVo toHostProfileVo() {
        return HostProfileVo.from(this);
    }


    public static Host toEntity(CreateHostRequest createHostRequest, UUID masterUserId) {
        return Host.builder()
            .name(createHostRequest.name())
            .contactEmail(createHostRequest.contactEmail())
            .contactNumber(createHostRequest.contactNumber())
            .masterUserId(masterUserId)
            .build();
    }


    @Builder
    public Host(
        String name,
        String introduce,
        String contactEmail,
        String contactNumber,
        UUID masterUserId) {
        this.profile =
            HostProfile.builder()
                .name(name)
                .introduce(introduce)
                .contactEmail(contactEmail)
                .contactNumber(contactNumber)
                .build();
        this.masterUserId = masterUserId;

    }

}
