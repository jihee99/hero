package com.ex.hero.order.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    //가맹점 코드, 10자
    private String cid;
    // 가맹점 코드 인증키 null
    private String cidSecret;
    // 주문 번호
    private String partnerOrderId;
    // 회원 아이디
    private String partnerUserId;
    // 상품명
    private String itemName;
    // 상품 코드
    private String itemsCode;
    // 수량
    private Long quantity;
    // 총액
    private Long totalAmount;
    // 상품 비과세 금액
    private Long taxFreeAmount;
    // 결제 성공 시 반환 url
    private String approvalUrl;
    // 결제 취소 시 반환 url
    private String cancelUrl;
    // 결제 실패 시 반환 url
    private String failUrl;
}
