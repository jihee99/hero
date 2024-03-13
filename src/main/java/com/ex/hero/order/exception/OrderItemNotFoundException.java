package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class OrderItemNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new OrderItemNotFoundException();

    private OrderItemNotFoundException() {
        super(OrderErrorCode.ORDER_ITEM_NOT_FOUND);
    }
}
