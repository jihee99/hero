package com.ex.hero.order.model.dto.response;

import com.ex.hero.common.vo.EventProfileVo;
import com.ex.hero.events.model.Event;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponse {

	// 총 결제 정보
	// @Schema(description = "결제 정보")
	// private final OrderPaymentResponse paymentInfo;

	@Schema(description = "예매 정보( 티켓 목록 )")
	private final List<OrderTicketResponse> tickets;

	// 예매 취소 정보
	// @Schema(description = "예매 취소 정보")
	// private final RefundInfoVo refundInfo;

	@Schema(description = "이벤트 프로필 정보")
	private final EventProfileVo eventProfile;

	@Schema(description = "주문 고유 uuid")
	private final String orderUuid;

	@Schema(description = "주문 번호")
	private final String orderNo;

	@Schema(description = "주문 방식 ( 결제 방식 , 승인 방식 )")
	private final OrderMethod orderMethod;

	public static OrderResponse of(
		Order order, Event event, List<OrderTicketResponse> tickets) {
		return OrderResponse.builder()
			.orderMethod(order.getOrderMethod())
			.tickets(tickets)
			.orderUuid(order.getUuid())
			.orderNo(order.getOrderNo())
			.eventProfile(event.toEventProfileVo())
			.build();
	}
}
