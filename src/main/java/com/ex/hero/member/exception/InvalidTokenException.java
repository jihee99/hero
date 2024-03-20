package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;

public class InvalidTokenException extends HeroException {
    public static final HeroException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(MemberErrorCode.INVALID_TOKEN);
    }
}