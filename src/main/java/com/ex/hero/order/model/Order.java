package com.ex.hero.order.model;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.order.exception.OrderItemNotFoundException;
import com.ex.hero.order.service.OrderValidationService;
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

import com.ex.hero.common.vo.Money;
import com.ex.hero.ticket.model.TicketItem;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_order")
public class Order {
    public static final Long NO_START_NUMBER = 1000000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private String uuid;

    private String orderNo;

    @Column(nullable = false)
    private String orderName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime approvedAt;

     private LocalDateTime withDrawAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderMethod orderMethod; // 주문타입

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.READY; // 주문상태

    @Embedded private PaymentInfo totalPaymentInfo;

    @PrePersist
    public void addUUID() {
        this.uuid = UUID.randomUUID().toString();
    }

    @PostPersist
    public void createOrder() {
        this.orderNo = "C" + Long.sum(NO_START_NUMBER, this.id);
    }

    @Builder
    public Order(
        Long userId,
        String orderName,
        List<OrderItem> orderItems,
        OrderStatus orderStatus,
        OrderMethod orderMethod,
        Long eventId
    ) {
        this.userId = userId;
        this.orderName = orderName;
        this.orderItems.addAll(orderItems);
        this.orderStatus = orderStatus;
        this.orderMethod = orderMethod;
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

    private OrderItem getOrderItem() {
        return orderItems.stream()
            .findFirst()
            .orElseThrow(() -> OrderItemNotFoundException.EXCEPTION);
    }

    @NotNull
    private static List<OrderItem> getOrderItems(Cart cart, TicketItem item) {
        return cart.getCartItems().stream()
                .map(cartItem -> OrderItem.of(cartItem, item))
                .toList();
    }

    public Long getItemId() {
        return getOrderItem().getItemId();
    }

    public Long getTotalQuantity() {
        return orderItems.stream().map(OrderItem::getQuantity).reduce(0L, Long::sum);
    }





    public static Order createPaymentOrder(
            Long userId, Cart cart, TicketItem item, OrderValidationService orderValidator
    ){
        Order order = Order.builder()
                .userId(userId)
                .orderName(cart.getCartName())
                .orderItems(getOrderItems(cart, item))
                .orderStatus(OrderStatus.PENDING_PAYMENT)
                .orderMethod(OrderMethod.PAYMENT)
                .eventId(item.getEventId())
                .build();
        orderValidator.validCreate(order);
        order.calculatePaymentInfo();
        return order;
    }


    public static Order createApproveOrder(
            Long userId, Cart cart, TicketItem item, OrderValidationService orderValidator
            ) {
        Order order = Order.builder()
                .userId(userId)
                .orderName(cart.getCartName())
                .orderItems(getOrderItems(cart, item))
                .orderStatus(OrderStatus.PENDING_APPROVE)
                .orderMethod(OrderMethod.APPROVAL)
                .eventId(item.getEventId())
                .build();

        orderValidator.validCreate(order);
//        orderValidator.validApproveStatePurchaseLimit(order);
        order.calculatePaymentInfo();
        return order;
    }





    public List<Long> getDistinctItemIds() {
        return this.orderItems.stream().map(OrderItem::getItemId).distinct().toList();
    }

    public Long getItemGroupId() {
        return getOrderItem().getItemGroupId();
    }

    public void calculatePaymentInfo() {
        totalPaymentInfo =
                PaymentInfo.builder()
                        .paymentAmount(getTotalPaymentPrice())
                        .supplyAmount(getTotalSupplyPrice())
                        .build();
    }

    public void approve(OrderValidationService orderValidator){
//        issueDoneOrderEvent();
        orderValidator.validCanApproveOrder(this);
        this.approvedAt = LocalDateTime.now();
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void freeConfirm(Long currentUserId, OrderValidationService orderValidator) {
        // 선착순 방식의 0원 결제시
        orderValidator.validOwner(this, currentUserId);
        /*주문상태가 0일때*/
        orderValidator.validCanFreeConfirm(this);
        this.approvedAt = LocalDateTime.now();
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void refuse(OrderValidationService orderValidator) {
        orderValidator.validCanRefuse(this);
        this.orderStatus = OrderStatus.CANCELED;
        this.withDrawAt = LocalDateTime.now();
    }


    public Boolean isNeedPaid() {
        // 총 금액이 0 보다 큰지
        return Money.ZERO.isLessThan(getTotalPaymentPrice()) && orderMethod.isPayment();
    }

    public Boolean isPaid(){
        return Money.ZERO.isLessThan(getTotalPaymentPrice()) && orderMethod.isPayment();
    }


}
