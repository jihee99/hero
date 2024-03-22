package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class SecurityContextNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new SecurityContextNotFoundException();

    private SecurityContextNotFoundException() { super(UserErrorCode.SECURITY_CONTEXT_NOT_FOUND);}
}
