package com.ex.hero.common.exception;

import com.ex.hero.user.exception.UserErrorCode;

public class ExpiredTokenException extends HeroException {
    public static final HeroException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(UserErrorCode.TOKEN_EXPIRED);
    }
}
