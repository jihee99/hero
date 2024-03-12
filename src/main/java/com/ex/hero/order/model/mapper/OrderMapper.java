package com.ex.hero.order.model.mapper;

import java.util.List;
import java.util.UUID;

import com.ex.hero.events.model.Event;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.dto.response.CreateOrderResponse;
import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.service.CommonOrderService;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.CommonTicketItemService;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class OrderMapper {

	private final CommonOrderService commonOrderService;
	private final CommonMemberService commonMemberService;
	private final CommonTicketItemService commonTicketItemService;

	@Transactional(readOnly = true)
	public CreateOrderResponse toCreateOrderResponse(UUID orderId) {
		Order order = commonOrderService.findById(orderId);
		Member member = commonMemberService.queryMember(order.getUserId());
		TicketItem item = commonTicketItemService.queryTicketItem(order.getItemId());
		return CreateOrderResponse.from(order, item, member);
	}


}
