package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class GroupNotFoundException extends HeroException {

    public static final HeroException EXCEPTION = new GroupNotFoundException();

    private GroupNotFoundException() {
        super(GroupErrorCode.GROUP_NOT_FOUND);
    }
}
