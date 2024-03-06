package com.ex.hero.host.model;

import java.util.UUID;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.host.exception.AlreadyJoinedHostException;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "host_user")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"host_id", "member_id"})})
public class HostUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "host_user_id")
    private UUID id;

    // 소속 호스트 아이디
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id")
    private Host host;

    // 소속 호스트를 관리중인 유저 아이디
    @Column(name = "member_id")
    private UUID userId;

    // 초대 승락 여부
    private Boolean active = Boolean.FALSE;

    // 유저의 권한
    @Enumerated(EnumType.STRING)
    private HostRole role = HostRole.GUEST;

    public void setHostRole(HostRole role) {
        this.role = role;
    }

    public void activate() {
        if (this.active) throw AlreadyJoinedHostException.EXCEPTION;
        this.active = true;
        // Events.raise(HostUserJoinEvent.of(this.host.getId(), this.getUserId()));
    }



    @Builder
    public HostUser(Host host, UUID userId, HostRole role) {
        this.host = host;
        this.userId = userId;
        this.role = role;
    }
}
