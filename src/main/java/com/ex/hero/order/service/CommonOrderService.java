package com.ex.hero.order.service;

import java.util.List;
import java.util.UUID;

import com.ex.hero.order.model.OrderItem;
import com.ex.hero.order.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import com.ex.hero.order.model.Order;
import com.ex.hero.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonOrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public Order findById(UUID orderId) {
		return orderRepository
			.findById(orderId)
			.orElseThrow(() -> new RuntimeException());
		// OrderNotFoundException
	}

	public List<Order> findByEventId(Long eventId){
		return orderRepository.findByEventId(eventId);
	}

	public List<Order> findByIdIn(List<String> orderIds) {
		return orderRepository.findByIdIn(orderIds);
	}

	public OrderItem queryOrderItem(Long orderItemId, Long userId) {
		return orderItemRepository
				.findById(orderItemId)
//				.findByIdAndUserId(orderItemId, userId)
				.orElseThrow(() -> new RuntimeException());
		// OrderItemNotFoundException.EXCEPTION
	}


}
