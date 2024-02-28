package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class ForbiddenHostException extends HeroException {

    public static final HeroException EXCEPTION = new ForbiddenHostException();

    private ForbiddenHostException() {
        super(HostErrorCode.FORBIDDEN_HOST);
    }
}
