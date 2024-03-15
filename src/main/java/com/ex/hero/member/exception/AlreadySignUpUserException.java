package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;
import com.ex.hero.member.model.Member;

public class AlreadySignUpUserException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadySignUpUserException();

    private AlreadySignUpUserException(){
        super(MemberErrorCode.USER_ALREADY_SIGNUP);
    }
}
