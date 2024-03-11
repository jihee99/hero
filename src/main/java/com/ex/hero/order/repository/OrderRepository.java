package com.ex.hero.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	List<Order> findByEventId(Long eventId);

	List<Order> findByIdIn(List<String> orderIds);
}
