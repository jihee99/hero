package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class EventOpenTimeExpiredException extends HeroException {

    public static final HeroException EXCEPTION = new EventOpenTimeExpiredException();

    private EventOpenTimeExpiredException() {
        super(EventErrorCode.OPEN_TIME_EXPIRED);
    }
}
