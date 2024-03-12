package com.ex.hero.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.ast.Or;

import com.ex.hero.common.vo.Money;
import com.ex.hero.order.service.OrderValidator;
import com.ex.hero.ticket.model.TicketItem;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private String orderName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime approvedAt;

    // 철회된 시간
    private LocalDateTime withDrawAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderMethod orderMethod; // 주문타입

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    @Builder
    public Order(
        Long userId,
        String orderName,
        List<OrderItem> orderItems,
        Long eventId) {
        this.userId = userId;
        this.orderName = orderName;
        this.orderItems.addAll(orderItems);
        this.eventId = eventId;
    }

    public Money getTotalSupplyPrice() {
        return orderItems.stream()
            .map(OrderItem::getTotalOrderPrice)
            .reduce(Money.ZERO, Money::plus);
    }

    public Money getTotalPaymentPrice() {
        return getTotalSupplyPrice();
    }

    public Boolean isPaid(){
        return Money.ZERO.isLessThan(getTotalPaymentPrice()) && orderMethod.isPayment();
    }

    private OrderItem getOrderItem() {
        return orderItems.stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException());
        // OrderLineNotFountException.EXCEPTION
    }

    public Long getItemId() {
        return getOrderItem().getTicketId();
    }

//    public static Order createPaymentOrder(
//            Long userId, OrderItem orderItem OrderValidator orderValidator
//    ){
//
//    }

}
