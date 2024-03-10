package com.ex.hero.common.vo;

import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class IssuedTicketItemInfoVo {

    private Long ticketItemId;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Enumerated(EnumType.STRING)
    private TicketPayType payType;

    private String ticketName;

    private Money price;

    @Builder
    public IssuedTicketItemInfoVo(
            Long ticketItemId,
            TicketType ticketType,
            TicketPayType payType,
            String ticketName,
            Money price) {
        this.ticketItemId = ticketItemId;
        this.ticketType = ticketType;
        this.payType = payType;
        this.ticketName = ticketName;
        this.price = price;
    }

    public static IssuedTicketItemInfoVo from(TicketItem item) {
        return IssuedTicketItemInfoVo.builder()
                .ticketItemId(item.getId())
                .ticketType(item.getType())
                .payType(item.getPayType())
                .ticketName(item.getName())
                .price(item.getPrice())
                .build();
    }
}