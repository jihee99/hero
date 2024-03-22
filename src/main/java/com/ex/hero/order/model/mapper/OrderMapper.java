package com.ex.hero.order.model.mapper;

import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.dto.response.CreateOrderResponse;
import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.model.dto.response.OrderTicketResponse;
import com.ex.hero.order.service.CommonOrderService;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.CommonIssuedTicketService;
import com.ex.hero.ticket.service.CommonTicketItemService;
import com.ex.hero.user.model.User;
import com.ex.hero.user.service.CommonUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@RequiredArgsConstructor
public class OrderMapper {

	private final CommonOrderService commonOrderService;
	private final CommonUserService commonUserService;
	private final CommonTicketItemService commonTicketItemService;
	private final CommonEventService commonEventService;
	private final CommonIssuedTicketService commonIssuedTicketService;

	@Transactional(readOnly = true)
	public CreateOrderResponse toCreateOrderResponse(Long orderId) {
		Order order = commonOrderService.findById(orderId);
		User user = commonUserService.queryMember(order.getUserId());
		TicketItem item = commonTicketItemService.queryTicketItem(order.getItemId());
		return CreateOrderResponse.from(order, item, user);
	}

	public OrderResponse toOrderResponse(String orderUuid) {
		Order order = commonOrderService.findByOrderUuid(orderUuid);
		Event event = getEvent(order);

		List<OrderTicketResponse> orderTicketResponses = getOrderTicketResponses(order);

		return OrderResponse.of(order, event, orderTicketResponses);
	}



	
	private Event getEvent(Order order) {
		return commonEventService.findById(order.getItemGroupId());
	}

	private List<OrderTicketResponse> getOrderTicketResponses(Order order) {
		User user = commonUserService.queryMember(order.getUserId());
		return order.getOrderItems().stream()
			.map(
				orderLineItem ->
					OrderTicketResponse.of(
						order,
						orderLineItem,
						user.getName(),
						getTicketNoName(orderLineItem.getId())))
			.toList();
	}

	private String getTicketNoName(Long orderLineItemId) {
		return commonIssuedTicketService.findOrderIssuedTickets(orderLineItemId).getTicketNoName();
	}
}
