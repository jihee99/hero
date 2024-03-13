package com.ex.hero.cart.exception;

import com.ex.hero.common.exception.HeroException;

public class CartNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new CartNotFoundException();

    private CartNotFoundException() {
        super(CartErrorCode.CART_NOT_FOUND);
    }

}
