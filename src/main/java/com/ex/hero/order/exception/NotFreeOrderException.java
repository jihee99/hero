package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class NotFreeOrderException extends HeroException {

    public static final HeroException EXCEPTION = new NotFreeOrderException();

    private NotFreeOrderException(){
        super(OrderErrorCode.ORDER_NOT_FREE);
    }
}
