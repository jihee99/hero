// package com.ex.hero.events.host;
//
// import java.util.UUID;
//
// import com.ex.hero.common.aop.domainEvent.DomainEvent;
// import com.ex.hero.host.model.Host;
// import com.ex.hero.host.model.HostRole;
// import com.ex.hero.host.model.HostUser;
// import com.ex.hero.host.vo.GroupProfileVo;
//
// import lombok.Builder;
// import lombok.Getter;
// import lombok.ToString;
//
// @Getter
// @Builder
// @ToString
// public class HostUserInvitationEvent extends DomainEvent {
//     private final UUID userId;
//     private final HostRole role;
//     private final GroupProfileVo hostProfileVo;
//
//     public static HostUserInvitationEvent of(Host host, HostUser hostUser) {
//         return HostUserInvitationEvent.builder()
//                 .hostProfileVo(host.toHostProfileVo())
//                 .role(hostUser.getRole())
//                 .userId(hostUser.getUserId())
//                 .build();
//     }
// }
