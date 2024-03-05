package com.ex.hero.host.exception;

import com.ex.hero.common.exception.BaseErrorCode;
import com.ex.hero.common.exception.HeroException;

public class MessagingServerException extends HeroException {

    public static final HeroException EXCEPTION = new MessagingServerException();

    public MessagingServerException() {
        super(HostErrorCode.MESSAGING_SERVER_EXCEPTION);
    }
}
