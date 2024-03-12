package com.ex.hero.ticket.exception;

import com.ex.hero.common.exception.HeroException;

public class ForbiddenTicketItemDeleteException extends HeroException {

    public static final HeroException EXCEPTION = new ForbiddenTicketItemDeleteException();

    private ForbiddenTicketItemDeleteException() {
        super(TicketItemErrorCode.FORBIDDEN_TICKET_ITEM_DELETE);
    }
}
