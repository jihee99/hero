package com.ex.hero.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex.hero.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByEventId(Long eventId);

	List<Order> findByIdIn(List<String> orderIds);

	@Query("SELECT DISTINCT order FROM tbl_order order LEFT JOIN FETCH order.orderItems oli WHERE order.uuid = :orderUuid")
	Optional<Order> findByOrderUuid(@Param("orderUuid") String orderUuid);
}
