package com.ex.hero.order.model;

import java.util.List;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.Money;
import com.ex.hero.ticket.model.TicketItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_order_item")
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    private Money ticketPrice;

    // 상품 수량
    @Column(nullable = false)
    private Long quantity;


    @Builder
    public OrderItem(TicketItem item, Long quantity) {
        this.ticketId = item.getId();
        this.ticketPrice = item.getPrice();
        this.quantity = quantity;
    }


    @Builder
    public static OrderItem of(Order order, TicketItem ticketItem) {
        List<OrderItem> orderItems = order.getOrderItems().stream().map(OrderItem::from).toList();
        return OrderLineItem.builder()
            .orderOptionAnswer(orderOptionAnswers)
            .quantity(cartLineItem.getQuantity())
            .orderItemVo(OrderItemVo.from(ticketItem))
            .build();
    }

    public static OrderItem from(Order order) {
        return OrderOptionAnswer.builder()
            .answer(cartOptionAnswer.getAnswer())
            .optionId(cartOptionAnswer.getOptionId())
            .additionalPrice(cartOptionAnswer.getAdditionalPrice())
            .build();
    }


    public Money getTotalOrderLinePrice() {
        return getTicketPrice().plus(ticketPrice).times(quantity);
    }

}
