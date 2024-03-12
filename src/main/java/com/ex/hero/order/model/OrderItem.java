package com.ex.hero.order.model;

import java.util.List;

import com.ex.hero.cart.model.CartItem;
import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.Money;
import com.ex.hero.common.vo.OrderItemVo;
import com.ex.hero.ticket.model.TicketItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_order_item")
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    // 상품
    @Embedded private OrderItemVo orderItem;

    // 상품 수량
    @Column(nullable = false)
    private Long quantity;

    @Builder
    public OrderItem(OrderItemVo orderItemVo, Long quantity) {
        this.orderItem = orderItemVo;
        this.quantity = quantity;
    }

    @Builder
    public static OrderItem of(CartItem cartItem, TicketItem ticketItem) {
        return OrderItem.builder()
                .quantity(cartItem.getQuantity())
                .orderItemVo(OrderItemVo.from(ticketItem))
                .build();
    }

    public Money getTotalOrderPrice() {
        return getItemPrice().times(quantity);
    }

    public Long getItemId() {
        return orderItem.getItemId();
    }

    public Long getItemGroupId() {
        return orderItem.getItemGroupId();
    }

    public Money getItemPrice() {
        return orderItem.getPrice();
    }


}
