package com.ex.hero.ticket.exception;

import com.ex.hero.common.exception.HeroException;

public class TicketItemNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new TicketItemNotFoundException();

    private TicketItemNotFoundException() {
        super(TicketItemErrorCode.TICKET_ITEM_NOT_FOUND);
    }
}
