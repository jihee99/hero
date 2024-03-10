package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotOpenEventException extends HeroException {

    public static final HeroException EXCEPTION = new CannotOpenEventException();

    private CannotOpenEventException() {
        super(EventErrorCode.CANNOT_OPEN_EVENT);
    }
}
