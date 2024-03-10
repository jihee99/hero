package com.ex.hero.ticket.model;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.IssuedTicketItemInfoVo;
import com.ex.hero.common.vo.IssuedTicketUserInfoVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private IssuedTicketStatus issuedTicketStatus = IssuedTicketStatus.ENTRANCE_INCOMPLETE;
}
