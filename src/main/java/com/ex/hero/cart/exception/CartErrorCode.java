package com.ex.hero.cart.exception;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {

    @ExplainError("한 장바구니엔 관련된 한 아이템만 올수 있습니다.")
    CART_INVALID_ITEM_KIND_POLICY(HttpStatus.BAD_REQUEST, 4001, "장바구니에 아이템을 담는 정책을 위반하였습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, 4002, "장바구니 안에 상품을 찾을 수 없습니다."),
    @ExplainError("id로 카트를 찾을 때 못 찾으면 발생하는 오류입니다.")
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, 4041, "장바구니를 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private int errorCode;
    private String errorMessage;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().errorCode(errorCode).errorMessage(errorMessage).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getErrorMessage();
    }
}
