package com.ex.hero.ticket.model.exception;

import com.ex.hero.common.exception.HeroException;

public class InvalidTicketTypeException extends HeroException {

    public static final HeroException EXCEPTION = new InvalidTicketTypeException();
    private InvalidTicketTypeException() {
        super(TicketItemErrorCode.INVALID_TICKET_TYPE);
    }
}
