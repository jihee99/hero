package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadySignUpUserException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadySignUpUserException();

    private AlreadySignUpUserException(){
        super(UserErrorCode.USER_ALREADY_SIGNUP);
    }
}
