package com.ex.hero.cart.model;

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
@Entity(name = "tbl_cart_item")
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Money itemPrice;

    @Column(nullable = false)
    private Long quantity;

    @Builder
    public CartItem(TicketItem item, Long quantity) {
        this.itemId = item.getId();
        this.itemPrice = item.getPrice();
        this.quantity = quantity;
    }

    public Money getTotalCartPrice() {
        return getItemPrice().times(quantity);
    }

    public Boolean isNeedPaid() {
        // 0 < totalCartLinePrice
        return Money.ZERO.isLessThan(getTotalCartPrice());
    }


}
