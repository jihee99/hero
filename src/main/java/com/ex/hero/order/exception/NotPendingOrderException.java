package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class NotPendingOrderException extends HeroException {

    public static final HeroException EXCEPTION = new NotPendingOrderException();

    private NotPendingOrderException() {
        super(OrderErrorCode.ORDER_NOT_PENDING);
    }

}
