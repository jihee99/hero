package com.ex.hero.order.exception;

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
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_MINE(HttpStatus.BAD_REQUEST, 4000, "주문 회원이 일치하지 않습니다."),
    @ExplainError("결제 금액과 , 주문금액이 다를 때 등 올바르지 않은 주문 상태를 가질 때 발생하는 오류입니다.")
    ORDER_NOT_VALID(HttpStatus.BAD_REQUEST, 4001, "올바르지 않은 주문입니다."),
    ORDER_NOT_PENDING(HttpStatus.BAD_REQUEST, 4002, " 결제,승인 대기 중인 주문이 아닙니다."),
    ORDER_NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, 4003, "지원하지 않는 방식의 주문입니다."),
    ORDER_CANNOT_CANCEL(HttpStatus.BAD_REQUEST, 4004, "주문을 취소할 수 없는 상태입니다."),
    ORDER_CANNOT_REFUND(HttpStatus.BAD_REQUEST, 4005, "주문을 환불할 수 없는 상태입니다."),
    ORDER_NOT_APPROVAL(HttpStatus.BAD_REQUEST, 4006, "승인 주문이 아닙니다."),
    ORDER_NOT_PAYMENT(HttpStatus.BAD_REQUEST, 4007, "결제 주문이 아닙니다."),
    ORDER_NOT_REFUND_DATE(HttpStatus.BAD_REQUEST, 4008, "환불을 할 수 있는 기한을 지났습니다."),
    ORDER_NOT_FREE(HttpStatus.BAD_REQUEST, 4009, "무료 주문이 아닙니다."),
    @ExplainError("장바구니 정책을 위반하였을 때 발생하는 오류입니다. 하나의 장바구니에는 관련된 하나의 이벤트만 담을 수 있음")
    ORDER_INVALID_ITEM_KIND_POLICY(HttpStatus.BAD_REQUEST, 4010, "장바구니 정책을 위반하였습니다."),
    CAN_NOT_DELETED_USER_APPROVE(HttpStatus.BAD_REQUEST, 4011, "유저가 탈퇴를 했습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 4040, "주문을 찾을 수 없습니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, 4041, "주문상품을 찾을 수 없습니다."),
    ;

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
