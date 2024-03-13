package com.ex.hero.ticket.exception;

import com.ex.hero.common.exception.HeroException;

public class TicketItemQuantityException extends HeroException {

    public static final HeroException EXCEPTION = new TicketItemQuantityException();

    private TicketItemQuantityException() {
        super(TicketItemErrorCode.TICKET_ITEM_QUANTITY_LESS_THAN_ZERO);
    }
}
