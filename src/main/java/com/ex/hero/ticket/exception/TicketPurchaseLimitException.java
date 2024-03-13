package com.ex.hero.ticket.exception;

import com.ex.hero.common.exception.HeroException;

public class TicketPurchaseLimitException extends HeroException {

    public static final HeroException EXCEPTION = new TicketPurchaseLimitException();

    private TicketPurchaseLimitException(){
        super(TicketItemErrorCode.TICKET_ITEM_PURCHASE_LIMIT);
    }
}
