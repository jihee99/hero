package com.ex.hero.host.exception;

import com.ex.hero.common.exception.HeroException;

public class CannotModifyMasterHostRoleException extends HeroException {

    public static final HeroException EXCEPTION = new CannotModifyMasterHostRoleException();

    private CannotModifyMasterHostRoleException() {
        super(HostErrorCode.CANNOT_MODIFY_MASTER_HOST_ROLE);
    }
}
