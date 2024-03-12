package com.ex.hero.common.vo;

import com.ex.hero.ticket.model.TicketItem;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class OrderItemVo {

    private String name;

    private Money price;

    private Long itemGroupId;

    private Long itemId;

    @Builder
    public OrderItemVo(String name, Money price, Long itemGroupId, Long itemId) {
        this.name = name;
        this.price = price;
        this.itemGroupId = itemGroupId;
        this.itemId = itemId;
    }

    public static OrderItemVo from(TicketItem ticketItem) {
        return OrderItemVo.builder()
                .itemGroupId(ticketItem.getEventId())
                .itemId(ticketItem.getId())
                .price(ticketItem.getPrice())
                .name(ticketItem.getName())
                .build();
    }
}