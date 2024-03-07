package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class NotMasterGroupException extends HeroException {

    public static final HeroException EXCEPTION = new NotMasterGroupException();

    private NotMasterGroupException() {
        super(GroupErrorCode.NOT_MASTER_GROUP);
    }
}
