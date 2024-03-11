package com.ex.hero.ticket.model.dto.response;

import com.ex.hero.common.vo.Money;
import com.ex.hero.member.model.Member;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketItemResponse {
	@Schema(description = "티켓상품 id")
	private final Long ticketItemId;

	@Schema(description = "티켓 지불 타입")
	private final TicketPayType payType;

	@Schema(description = "이름")
	private final String ticketName;

	@Schema(description = "설명")
	private final String description;

	@Schema(description = "가격")
	private final Money price;

	@Schema(description = "티켓 승인 타입")
	private final TicketType approveType;

	@Schema(description = "1인당 구매 제한 매수")
	private final Long purchaseLimit;

	@Schema(description = "공급량")
	private final Long supplyCount;

	@Schema(description = "재고")
	private final Long quantity;

	@Schema(description = "재고가 감소한 티켓인지 리턴")
	private final Boolean isSold;

	@Schema(description = "재고가 남아있는지 리턴")
	private final Boolean isQuantityLeft;

	public static TicketItemResponse from(TicketItem ticketItem, Boolean isAdmin) {

		return TicketItemResponse.builder()
				.ticketItemId(ticketItem.getId())
				.payType(ticketItem.getPayType())
				.ticketName(ticketItem.getName())
				.description(ticketItem.getDescription())
				.price(ticketItem.getPrice())
				.approveType(ticketItem.getType())
				.purchaseLimit(ticketItem.getPurchaseLimit())
				.supplyCount(ticketItem.getSupplyCount())
				.isSold(ticketItem.isSold())
				.isQuantityLeft(ticketItem.isQuantityLeft())
				.build();
	}
}
