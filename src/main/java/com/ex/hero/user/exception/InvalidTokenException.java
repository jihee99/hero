package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class InvalidTokenException extends HeroException {
    public static final HeroException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(UserErrorCode.INVALID_TOKEN);
    }
}
