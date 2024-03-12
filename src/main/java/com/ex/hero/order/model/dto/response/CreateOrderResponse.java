package com.ex.hero.order.model.dto.response;

import java.util.UUID;

import com.ex.hero.common.vo.Money;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.Profile;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderMethod;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderResponse {
	@Schema(description = "UUId")
	private final String orderId;

	@Schema(description = "상품명")
	private final String orderName;

	@Schema(description = "주문자 이메일")
	private final String userEmail;

	@Schema(description = "주문자 성함")
	private final String userName;

	@Schema(description = "결제 금액")
	private final Money amount;

	@Schema(description = "결제 여부")
	private final Boolean isNeedPayment;

	@Schema(description = "주문타입")
	private final OrderMethod orderMethod;

	@Schema(description = "승인 타입")
	private final TicketType approveType;

	@Schema(description = "지불방식")
	private final TicketPayType ticketPayType;

	public static CreateOrderResponse from(Order order, TicketItem item, Member member) {
		return CreateOrderResponse.builder()
			.userEmail(member.getEmail())
			.userName(member.getName())
			.orderName(order.getOrderName())
			.orderId(order.getUuid())
			.amount(order.getTotalPaymentPrice())
			.orderMethod(order.getOrderMethod())
			.isNeedPayment(order.isNeedPaid())
			.approveType(item.getType())
			.ticketPayType(item.getPayType())
			.build();
	}
}
