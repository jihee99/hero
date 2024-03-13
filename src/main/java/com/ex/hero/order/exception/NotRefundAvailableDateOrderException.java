package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class NotRefundAvailableDateOrderException extends HeroException {

    public static final HeroException EXCEPTION = new NotRefundAvailableDateOrderException();

    private NotRefundAvailableDateOrderException(){
        super(OrderErrorCode.ORDER_NOT_REFUND_DATE);
    }

}
