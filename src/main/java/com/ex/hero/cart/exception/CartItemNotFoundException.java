package com.ex.hero.cart.exception;

import com.ex.hero.common.exception.HeroException;

public class CartItemNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new CartItemNotFoundException();

    private CartItemNotFoundException() {
        super(CartErrorCode.CART_ITEM_NOT_FOUND);
    }
}
