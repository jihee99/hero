package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class MessagingServerException extends HeroException {

    public static final HeroException EXCEPTION = new MessagingServerException();

    public MessagingServerException() {
        super(GroupErrorCode.MESSAGING_SERVER_EXCEPTION);
    }
}
