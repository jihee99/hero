package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class PasswordFormatMismatchException extends HeroException {

    public static final HeroException EXCEPTION = new PasswordFormatMismatchException();

    public PasswordFormatMismatchException() {
        super(UserErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
