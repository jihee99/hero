package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class GroupUserNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new GroupUserNotFoundException();

    private GroupUserNotFoundException() {
        super(GroupErrorCode.GROUP_USER_NOT_FOUND);
    }
}
