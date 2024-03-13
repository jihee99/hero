package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class OrdeItemNotOneTypeException extends HeroException {

    public static final HeroException EXCEPTION = new OrdeItemNotOneTypeException();
    private OrdeItemNotOneTypeException() {
        super(OrderErrorCode.ORDER_INVALID_ITEM_KIND_POLICY);
    }
}
