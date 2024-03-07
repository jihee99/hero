package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class ForbiddenGroupException extends HeroException {

    public static final HeroException EXCEPTION = new ForbiddenGroupException();

    private ForbiddenGroupException() {
        super(GroupErrorCode.FORBIDDEN_GROUP);
    }
}
