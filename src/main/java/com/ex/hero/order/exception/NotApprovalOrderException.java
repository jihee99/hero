package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class NotApprovalOrderException extends HeroException {
    public static final HeroException EXCEPTION = new NotApprovalOrderException();

    private NotApprovalOrderException() {
        super(OrderErrorCode.ORDER_NOT_APPROVAL);
    }
}
