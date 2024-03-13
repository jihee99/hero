package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class OrderNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new OrderNotFoundException();

    private OrderNotFoundException() {
        super(OrderErrorCode.ORDER_NOT_FOUND);
    }
}