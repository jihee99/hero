package com.ex.hero.common.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final int code;
    private final String reason;
    private final LocalDateTime timeStamp;

    private final String path;

    public ErrorResponse(ErrorReason errorReason, String path) {
        this.status = errorReason.getHttpStatus().value();
        this.code = errorReason.getErrorCode();
        this.reason = errorReason.getErrorMessage();
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }

    public ErrorResponse(int status, int code, String reason, String path) {
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }
}
