package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotDeleteByOpenEventException extends HeroException {

    public static final HeroException EXCEPTION = new CannotDeleteByOpenEventException();

    private CannotDeleteByOpenEventException() {
        super(EventErrorCode.CANNOT_DELETE_BY_OPEN_EVENT);
    }
}
