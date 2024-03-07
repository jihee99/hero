package com.ex.hero.ticket.dto.response;

import java.util.UUID;

import com.ex.hero.common.vo.Money;
import com.ex.hero.member.model.Member;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;

public record TicketItemResponse (
	@Schema(description = "티켓상품 id")
	Long id,
	Member member,
	TicketPayType payType,
	String name,
	String description,
	Money price,
	TicketType type,
	Long purchaseLimit,
	Long supplyCount,
	Long quantity,
	Boolean isSold,
	Boolean isQuantityLeft
) {
}
