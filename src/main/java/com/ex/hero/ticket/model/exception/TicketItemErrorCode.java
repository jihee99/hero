package com.ex.hero.ticket.model.exception;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.ex.hero.common.exception.HeroStatic.BAD_REQUEST;
import static com.ex.hero.common.exception.HeroStatic.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum TicketItemErrorCode implements BaseErrorCode {
    @ExplainError("요청에서 보내준 티켓 상품 id 값이 올바르지 않을 때 발생하는 오류입니다.")
    TICKET_ITEM_NOT_FOUND(NOT_FOUND, "Ticket_Item_404_1", "티켓 아이템을 찾을 수 없습니다."),
    @ExplainError("설정할수 없는 티켓 가격일때 발생하는 오류입니다.")
    INVALID_TICKET_PRICE(BAD_REQUEST, "Ticket_Item_400_1", "설정할 수 없는 티켓 가격입니다."),
    @ExplainError("옵션을 적용할 상품이 해당 이벤트 소속이 아닐 때 발생하는 오류입니다.")
    INVALID_TICKET_ITEM(BAD_REQUEST, "Ticket_Item_400_2", "해당 이벤트 소속 티켓상품이 아닙니다."),
    TICKET_ITEM_PURCHASE_LIMIT(BAD_REQUEST, "Ticket_Item_400_3", "해당 티켓상품 최대 구매 가능 갯수를 넘었습니다."),
    @ExplainError("이미 재고가 감소되어 옵션 변경이 불가능할 경우 발생하는 오류입니다.")
    FORBIDDEN_OPTION_CHANGE(BAD_REQUEST, "Item_Option_Group_400_2", "옵션 변경이 불가능한 상태입니다."),
    @ExplainError("이미 재고가 감소되어 티켓상품 삭제가 불가능할 경우 발생하는 오류입니다.")
    FORBIDDEN_TICKET_ITEM_DELETE(BAD_REQUEST, "Ticket_Item_400_4", "티켓상품 삭제가 불가능한 상태입니다."),
    @ExplainError("티켓 지불방식과 승인방식이 불가능한 조합일때 발생하는 오류입니다.")
    INVALID_TICKET_TYPE(BAD_REQUEST, "Ticket_Item_400_5", "잘못된 티켓 승인타입입니다.");

    private Integer status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
