package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class UserNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
