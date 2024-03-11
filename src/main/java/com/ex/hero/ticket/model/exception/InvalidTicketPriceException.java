package com.ex.hero.ticket.model.exception;

import com.ex.hero.common.exception.HeroException;

public class InvalidTicketPriceException extends HeroException {

    public static final HeroException EXCEPTION = new InvalidTicketPriceException();
    private InvalidTicketPriceException() {
        super(TicketItemErrorCode.INVALID_TICKET_PRICE);
    }
}
