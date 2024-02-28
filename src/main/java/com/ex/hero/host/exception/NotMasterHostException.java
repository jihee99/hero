package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class NotMasterHostException extends HeroException {

    public static final HeroException EXCEPTION = new NotMasterHostException();

    private NotMasterHostException() {
        super(HostErrorCode.NOT_MASTER_HOST);
    }
}
