package com.ex.hero.common.exception;

import com.ex.hero.user.exception.UserErrorCode;

public class RefreshTokenExpiredException extends HeroException {
    public static final HeroException EXCEPTION = new RefreshTokenExpiredException();

    private RefreshTokenExpiredException() {
        super(UserErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}
