package com.ex.hero.order.model.mapper;

import java.util.List;
import java.util.UUID;

import com.ex.hero.events.model.Event;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.dto.response.CreateOrderResponse;
import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.model.dto.response.OrderTicketResponse;
import com.ex.hero.order.service.CommonOrderService;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.CommonIssuedTicketService;
import com.ex.hero.ticket.service.CommonTicketItemService;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class OrderMapper {

	private final CommonOrderService commonOrderService;
	private final CommonMemberService commonMemberService;
	private final CommonTicketItemService commonTicketItemService;
	private final CommonEventService commonEventService;
	private final CommonIssuedTicketService commonIssuedTicketService;

	@Transactional(readOnly = true)
	public CreateOrderResponse toCreateOrderResponse(Long orderId) {
		Order order = commonOrderService.findById(orderId);
		Member member = commonMemberService.queryMember(order.getUserId());
		TicketItem item = commonTicketItemService.queryTicketItem(order.getItemId());
		return CreateOrderResponse.from(order, item, member);
	}

	@Transactional(readOnly = true)
	public OrderResponse toOrderResponse(String orderUuid) {
		Order order = commonOrderService.findByOrderUuid(orderUuid);
		Event event = getEvent(order);

		List<OrderTicketResponse> orderLineTicketResponses = getOrderTicketResponses(order);

		return OrderResponse.of(order, event, orderLineTicketResponses);
	}






	
	private Event getEvent(Order order) {
		return commonEventService.findById(order.getItemGroupId());
	}

	private List<OrderTicketResponse> getOrderTicketResponses(Order order) {
		Member member = commonMemberService.queryMember(order.getUserId());
		return order.getOrderItems().stream()
			.map(
				orderLineItem ->
					OrderTicketResponse.of(
						order,
						orderLineItem,
						member.getName(),
						getTicketNoName(orderLineItem.getId())))
			.toList();
	}

	private String getTicketNoName(Long orderLineItemId) {
		return commonIssuedTicketService.findOrderIssuedTickets(orderLineItemId).getTicketNoName();
	}
}
