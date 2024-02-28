package com.ex.hero.member.exception;

import com.ex.hero.common.exception.HeroException;

public class SecurityContextNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new SecurityContextNotFoundException();

    private SecurityContextNotFoundException() { super(MemberErrorCode.SECURITY_CONTEXT_NOT_FOUND);}
}
