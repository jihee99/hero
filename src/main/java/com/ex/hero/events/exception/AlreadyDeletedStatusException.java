package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadyDeletedStatusException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyDeletedStatusException();

    private AlreadyDeletedStatusException() {
        super(EventErrorCode.ALREADY_DELETED_STATUS);
    }
}
