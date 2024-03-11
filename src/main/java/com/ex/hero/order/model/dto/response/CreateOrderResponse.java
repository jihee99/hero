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

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderResponse {
	private final UUID orderId;
	private final String orderName;
	private final String userEmail;
	private final String userName;
	private final Money amount;
	private final Boolean isNeedPayment;
	private final OrderMethod orderMethod;
	private final TicketType approveType;
	private final TicketPayType ticketPayType;

	public static CreateOrderResponse from(Order order, TicketItem item, Member member) {
		return CreateOrderResponse.builder()
			.userEmail(member.getEmail())
			.userName(member.getName())
			.orderName(order.getOrderName())
			.orderId(order.getId())
			.amount(order.getTotalPaymentPrice())
			.orderMethod(order.getOrderMethod())
			.isNeedPayment(order.isPaid())
			.approveType(item.getType())
			.ticketPayType(item.getPayType())
			.build();
	}
}
