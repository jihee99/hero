package com.ex.hero.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorReason {

    private final int errorCode;

    private final String errorMessage;

}
