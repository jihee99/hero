package com.ex.hero.order.exception;

import com.ex.hero.common.exception.HeroException;

public class CanNotApproveDeletedUserOrderException extends HeroException {

    public static final HeroException EXCEPTION = new CanNotApproveDeletedUserOrderException();

    private CanNotApproveDeletedUserOrderException(){
        super(OrderErrorCode.CAN_NOT_DELETED_USER_APPROVE);
    }
}
