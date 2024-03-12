package com.ex.hero.order.repository;

import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    Optional<OrderItem> findByIdAndUserId(Long orderItemId, Long userId);
}
