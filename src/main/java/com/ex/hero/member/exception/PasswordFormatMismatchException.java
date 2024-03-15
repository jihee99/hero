package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;

public class PasswordFormatMismatchException extends HeroException {

    public static final HeroException EXCEPTION = new PasswordFormatMismatchException();

    public PasswordFormatMismatchException() {
        super(MemberErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
