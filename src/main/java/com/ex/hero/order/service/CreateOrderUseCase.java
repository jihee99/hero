package com.ex.hero.order.service;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.cart.service.CommonCartService;
import com.ex.hero.common.util.UserUtils;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.dto.request.CreateOrderRequest;
import com.ex.hero.order.model.dto.response.CreateOrderResponse;
import com.ex.hero.order.model.mapper.OrderMapper;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.service.CommonTicketItemService;
import com.ex.hero.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

	private final UserUtils userUtils;
	private final CommonTicketItemService commonTicketItemService;
	private final CommonOrderService commonOrderService;
	private final CommonCartService commonCartService;
	private final OrderMapper orderMapper;
	private final OrderValidationService orderValidator;

	public CreateOrderResponse execute(CreateOrderRequest createOrderRequest) {
		User user = userUtils.getCurrentMember();
		Long cartId = createOrderRequest.getCartId();

		// 주문서 생성
		Order order = createOrder(cartId, user.getUserId());
		commonOrderService.save(order).getUuid();

		return orderMapper.toCreateOrderResponse(order.getId());
	}


	// 주문서 생성 함수
	private Order createOrder(Long cartId, Long userId){
		Cart cart = commonCartService.queryCart(cartId);
		TicketItem ticketItem = commonTicketItemService.queryTicketItem(cart.getItemId());
		TicketPayType payType = ticketItem.getPayType();

		if(payType == TicketPayType.FREE_TICKET){
			if (ticketItem.isFCFS()) {
				return Order.createPaymentOrder(userId, cart, ticketItem, orderValidator);
			}
			// 승인 티켓 (무료)
			return Order.createApproveOrder(userId, cart, ticketItem, orderValidator);
		}
		return Order.createPaymentOrder(userId, cart, ticketItem, orderValidator);
	}

}
