package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;

public class UserNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(MemberErrorCode.USER_NOT_FOUND);
    }
}
