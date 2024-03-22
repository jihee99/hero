package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotModifyOpenEventException extends HeroException {

    public static final HeroException EXCEPTION = new CannotModifyOpenEventException();
    public CannotModifyOpenEventException() {
        super(EventErrorCode.CANNOT_MODIFY_OPEN_EVENT);
    }
}
