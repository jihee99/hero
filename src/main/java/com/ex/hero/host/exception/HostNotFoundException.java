package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class HostNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new HostNotFoundException();

    private HostNotFoundException() {
        super(HostErrorCode.HOST_NOT_FOUND);
    }
}
