package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;


public class AlreadyJoinedGroupException extends HeroException {

    public static final HeroException EXCEPTION = new AlreadyJoinedGroupException();

    private AlreadyJoinedGroupException() {
        super(GroupErrorCode.ALREADY_JOINED_GROUP);
    }
}
