package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class NotManagerGroupException extends HeroException {

    public static final HeroException EXCEPTION = new NotManagerGroupException();

    private NotManagerGroupException() {
        super(GroupErrorCode.NOT_MANAGER_GROUP);
    }
}
