package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class ForbiddenUserException extends HeroException {

    public static final HeroException EXCEPTION = new ForbiddenUserException();

    private ForbiddenUserException() {
        super(UserErrorCode.USER_FORBIDDEN);
    }
}
