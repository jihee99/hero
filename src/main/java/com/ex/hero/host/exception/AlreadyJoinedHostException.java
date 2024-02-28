package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;


public class AlreadyJoinedHostException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyJoinedHostException();

    private AlreadyJoinedHostException() {
        super(HostErrorCode.ALREADY_JOINED_HOST);
    }
}
