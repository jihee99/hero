package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;

public class PasswordIncorrectException extends HeroException {

    public static final HeroException EXCEPTION = new PasswordFormatMismatchException();

    private PasswordIncorrectException(){
        super(MemberErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
