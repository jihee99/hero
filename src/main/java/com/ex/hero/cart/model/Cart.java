package com.ex.hero.cart.model;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_cart")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String cartName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Cart(Long userId, List<CartItem> cartItems) {
        this.userId = userId;
        this.cartItems.addAll(cartItems);
    }

    public static Cart of (
            List<CartItem> cartItems,
            String itemName,
            Long userId
    ) {
        Cart cart = Cart.builder().cartItems(cartItems).userId(userId).build();
        cart.updateCartName(itemName);
        return cart;
    }

    public Money getTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getTotalCartPrice)
                .reduce(Money.ZERO, Money::plus);
    }

    public Long getItemId() {
        return getCartItem().getItemId();
    }

    public void updateCartName(String name) {
        cartName = name;
    }

    public CartItem getCartItem() {
        return cartItems.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        // TODO 예외처리 추가
        // CartLineItemNotFoundException.EXCEPTION
    }

    public Boolean isNeedPaid() {
        return this.cartItems.stream()
                .map(CartItem::isNeedPaid)
                .reduce(Boolean.FALSE, (Boolean::logicalOr));
    }

    public Long getTotalQuantity() {
        return cartItems.stream().map(CartItem::getQuantity).reduce(0L, Long::sum);
    }


}
