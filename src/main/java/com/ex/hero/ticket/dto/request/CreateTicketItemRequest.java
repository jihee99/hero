package com.ex.hero.ticket.dto.request;

import com.ex.hero.member.model.Member;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTicketItemRequest (
	@Schema(nullable = false, defaultValue = "무료티켓")
	TicketPayType payType,
	Member member,
	@NotEmpty(message = "티켓상품 이름을 입력해주세요")
	@Schema(nullable = false, example = "일반 티켓")
	String name,
	@Nullable
	String description,
	@NotNull
	@Schema(defaultValue = "0", nullable = false, example = "4000")
	String price,
	@NotNull
	@Schema(nullable = false, example = "100")
	Long supplyCount,
	@NotNull
	@Schema(nullable = false, example = "1")
	Long purchaseLimit,
	TicketType type
) {

}
