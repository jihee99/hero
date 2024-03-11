package com.ex.hero.order.model;

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
    @Column(name = "cart_item_id")
    private Long id;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Money itemPrice;

    // 상품 수량
    @Column(nullable = false)
    private Long quantity;


    @Builder
    public OrderItem(TicketItem item, Long quantity) {
        this.itemId = item.getId();
        this.itemPrice = item.getPrice();
        this.quantity = quantity;
    }



}
