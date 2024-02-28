package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class NotAcceptedHostException extends HeroException {

    public static final HeroException EXCEPTION = new NotAcceptedHostException();

    private NotAcceptedHostException() {
        super(HostErrorCode.NOT_ACCEPTED_HOST);
    }
}
