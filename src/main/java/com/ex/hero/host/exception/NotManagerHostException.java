package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class NotManagerHostException extends HeroException {

    public static final HeroException EXCEPTION = new NotManagerHostException();

    private NotManagerHostException() {
        super(HostErrorCode.NOT_MANAGER_HOST);
    }
}
