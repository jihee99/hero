package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class CanNotCancelOrderException extends HeroException {

    public static final HeroException EXCEPTION = new CanNotCancelOrderException();

    private CanNotCancelOrderException(){
        super(OrderErrorCode.ORDER_CANNOT_CANCEL);
    }
}
