package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadyOpenStatusException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyOpenStatusException();

    private AlreadyOpenStatusException() {
        super(EventErrorCode.ALREADY_OPEN_STATUS);
    }
}
