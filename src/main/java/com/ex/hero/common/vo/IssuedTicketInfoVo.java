package com.ex.hero.common.vo;

import com.ex.hero.common.annotation.DateFormat;
import com.ex.hero.ticket.model.IssuedTicket;
import com.ex.hero.ticket.model.IssuedTicketStatus;
import com.ex.hero.ticket.model.TicketPayType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class IssuedTicketInfoVo {

    private final Long issuedTicketId;

    private final String issuedTicketNo;

    private final String uuid;

    private final String ticketName;

    private final TicketPayType payType;

    private final Money ticketPrice;

    @DateFormat
    private final LocalDateTime createdAt;

    @DateFormat
    private final LocalDateTime enteredAt;

    private final IssuedTicketStatus issuedTicketStatus;


    public static IssuedTicketInfoVo from(IssuedTicket issuedTicket) {
        return IssuedTicketInfoVo.builder()
                .issuedTicketId(issuedTicket.getId())
                .issuedTicketNo(issuedTicket.getIssuedTicketNo())
                .uuid(issuedTicket.getUuid())
                .ticketName(issuedTicket.getItemInfo().getTicketName())
                .payType(issuedTicket.getItemInfo().getPayType())
                .ticketPrice(issuedTicket.getItemInfo().getPrice())
                .createdAt(issuedTicket.getCreatedAt())
                .issuedTicketStatus(issuedTicket.getIssuedTicketStatus())
                .enteredAt(issuedTicket.getEnteredAt())
                .build();
    }
}
