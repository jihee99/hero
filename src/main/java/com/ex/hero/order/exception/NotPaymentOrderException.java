package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class NotPaymentOrderException extends HeroException {

    public static final HeroException EXCEPTION = new NotPaymentOrderException();

    private NotPaymentOrderException() {
        super(OrderErrorCode.ORDER_NOT_PAYMENT);
    }

}
