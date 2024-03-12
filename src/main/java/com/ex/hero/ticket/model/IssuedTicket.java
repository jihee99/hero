package com.ex.hero.ticket.model;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.IssuedTicketInfoVo;
import com.ex.hero.common.vo.IssuedTicketItemInfoVo;
import com.ex.hero.common.vo.IssuedTicketUserInfoVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="tbl_issued_ticket")
public class IssuedTicket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_ticket_id")
    private Long id;

    private String issuedTicketNo;

    private Long eventId;

    @Embedded
    private IssuedTicketUserInfoVo userInfo;

    @Embedded
    private IssuedTicketItemInfoVo itemInfo;

    // 발급 uuid ( 회원 > 티켓 발급 )
    private String orderUuid;

    private LocalDateTime enteredAt;

    private Long orderId;

    @Column(nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private IssuedTicketStatus issuedTicketStatus = IssuedTicketStatus.ENTRANCE_INCOMPLETE;

    @Builder
    public IssuedTicket(
            Long eventId,
            IssuedTicketUserInfoVo userInfo,
            String orderUuid,
            Long orderId,
            IssuedTicketItemInfoVo itemInfo,
            IssuedTicketStatus issuedTicketStatus) {
        this.eventId = eventId;
        this.userInfo = userInfo;
        this.itemInfo = itemInfo;
        this.orderUuid = orderUuid;
        this.orderId = orderId;
        this.issuedTicketStatus = issuedTicketStatus;
    }

    @PrePersist
    public void createUUID() {
        this.uuid = UUID.randomUUID().toString();
    }

    public IssuedTicketInfoVo toIssuedTicketInfoVo() {
        return IssuedTicketInfoVo.from(this);
    }
}
