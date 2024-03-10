package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadyPreparingStatusException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyPreparingStatusException();

    private AlreadyPreparingStatusException() {
        super(EventErrorCode.ALREADY_PREPARING_STATUS);
    }
}
