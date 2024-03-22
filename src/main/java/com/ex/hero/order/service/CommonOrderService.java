package com.ex.hero.order.service;

import com.ex.hero.order.exception.OrderItemNotFoundException;
import com.ex.hero.order.exception.OrderNotFoundException;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderItem;
import com.ex.hero.order.repository.OrderItemRepository;
import com.ex.hero.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonOrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public Order findById(Long orderId) {
		return orderRepository
			.findById(orderId)
			.orElseThrow(() -> OrderNotFoundException.EXCEPTION);
	}

	public List<Order> findByEventId(Long eventId) {
		return orderRepository.findByEventId(eventId);
	}

	public List<Order> findByIdIn(List<String> orderIds) {
		return orderRepository.findByIdIn(orderIds);
	}

	public OrderItem queryOrderItem(Long orderItemId, Long userId) {
		return orderItemRepository
			.findById(orderItemId)
			//				.findByIdAndUserId(orderItemId, userId)
			.orElseThrow(() -> OrderItemNotFoundException.EXCEPTION);
	}

	public Order findByOrderUuid(String orderUuid) {
		return orderRepository
			.findByOrderUuid(orderUuid)
			.orElseThrow(() -> OrderNotFoundException.EXCEPTION);
	}
}
