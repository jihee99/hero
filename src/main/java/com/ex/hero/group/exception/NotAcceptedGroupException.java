package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class NotAcceptedGroupException extends HeroException {

    public static final HeroException EXCEPTION = new NotAcceptedGroupException();

    private NotAcceptedGroupException() {
        super(GroupErrorCode.NOT_ACCEPTED_GROUP);
    }
}
