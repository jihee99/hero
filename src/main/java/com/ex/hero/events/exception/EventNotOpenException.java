package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class EventNotOpenException extends HeroException {

    public static final HeroException EXCEPTION = new EventNotOpenException();

    private EventNotOpenException() {
        super(EventErrorCode.EVENT_NOT_OPEN);
    }
}
