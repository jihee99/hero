package com.ex.hero.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReason {

    private final HttpStatus httpStatus;

    private final int errorCode;

    private final String errorMessage;

}
