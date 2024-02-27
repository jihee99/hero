package com.ex.hero.common.exception;

import com.ex.hero.common.dto.ErrorReason;


public interface BaseErrorCode {

    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
