package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class PasswordIncorrectException extends HeroException {

    public static final HeroException EXCEPTION = new PasswordFormatMismatchException();

    private PasswordIncorrectException(){
        super(UserErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
