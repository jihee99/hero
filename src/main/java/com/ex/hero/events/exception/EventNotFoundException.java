package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class EventNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new EventNotFoundException();

    private EventNotFoundException() {
        super(EventErrorCode.EVENT_NOT_FOUND);
    }
}
