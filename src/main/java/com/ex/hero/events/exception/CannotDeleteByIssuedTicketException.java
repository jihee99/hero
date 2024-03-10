package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotDeleteByIssuedTicketException extends HeroException {

    public static final HeroException EXCEPTION = new CannotDeleteByIssuedTicketException();

    private CannotDeleteByIssuedTicketException() {
        super(EventErrorCode.CANNOT_DELETE_BY_ISSUED_TICKET);
    }
}