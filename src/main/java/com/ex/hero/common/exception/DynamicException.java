package com.ex.hero.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DynamicException extends RuntimeException {
    private final int status;
    private final String code;
    private final String reason;
}
