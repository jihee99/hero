package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class HostUserNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new HostUserNotFoundException();

    private HostUserNotFoundException() {
        super(HostErrorCode.HOST_USER_NOT_FOUND);
    }
}
