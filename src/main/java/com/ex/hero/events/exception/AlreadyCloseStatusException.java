package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadyCloseStatusException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyCloseStatusException();

    private AlreadyCloseStatusException() {
        super(EventErrorCode.ALREADY_CLOSE_STATUS);
    }
}
