package com.ex.hero.group.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotModifyMasterGroupUserRoleException extends HeroException {

    public static final HeroException EXCEPTION = new CannotModifyMasterGroupUserRoleException();

    private CannotModifyMasterGroupUserRoleException() {
        super(GroupErrorCode.CANNOT_MODIFY_MASTER_GROUP_ROLE);
    }
}
