package com.ex.hero.ticket.model.exception;

import com.ex.hero.common.exception.HeroException;

public class InvalidTicketItemException extends HeroException {

    public static final HeroException EXCEPTION = new InvalidTicketItemException();

    private InvalidTicketItemException() {
        super(TicketItemErrorCode.INVALID_TICKET_ITEM);
    }
}
