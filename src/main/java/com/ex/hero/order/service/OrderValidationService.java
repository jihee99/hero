package com.ex.hero.order.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;
import com.ex.hero.order.exception.*;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderStatus;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.CommonIssuedTicketService;
import com.ex.hero.ticket.service.CommonTicketItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderValidationService {
    private final CommonEventService commonEventService;
    private final CommonTicketItemService commonTicketItemService;
    private final CommonIssuedTicketService commonIssuedTicketService;
    private final CommonMemberService commonMemberService;
    private final CommonOrderService commonOrderService;

    /* 주문 생성 가능 여부 */
    public void validCreate (Order order) {
        TicketItem item = getItem(order);
        Event event = getEvent(order);

        // 이벤트가 열려있는지
        validEventIsOpen(event);
        // 예매가능시간이 안 지났는지
        validTicketingTime(event);
        // 재고가 있는지
        validItemStockEnough(order, item);
        // 아이템의 종류가 1 종류 인지
        validItemKindIsOneType(order);
        // 아이템 구매 가능 갯수를 넘지 않았는지.
        validItemPurchaseLimit(order, item);
    }

    /* 주문 승인 가능 여부 */
    public void validCanApproveOrder(Order order) {
        validMethodIsCanApprove(order);
        validStatusCanApprove(getOrderStatus(order));
        validCanDone(order);
        // 유저가 탈퇴를 안했는지 확인.
        validUserNotDeleted(order);
    }

    /* 결제 방식의 주문인지 검증 */
    public void validCanConfirmPayment(Order order) {
        // 주문 방식이 결제 방식인지
        validMethodIsPaymentOrder(order);
        // 주문 상태가 결제 방식의 승인 상태인지
        validStatusCanPaymentConfirm(getOrderStatus(order));
        // 주문을 완료할 수 있는지
        validCanDone(order);
    }

    /* 선착순 방식의 무료 주문인지 검증 */
    public void validCanFreeConfirm(Order order) {
        // 무료인지
        validAmountIsFree(order);
        // 주문 상태가 결제 방식의 승인 인지
        validStatusCanPaymentConfirm(getOrderStatus(order));
        // 주문을 완료할 수 있는지
        validCanDone(order);
    }

    /* 주문을 완료할 수 있는지 공통검증 */
    public void validCanDone(Order order) {
        TicketItem item = getItem(order);
        Event event = getEvent(order);

        // 이벤트가 열려있는 상태인지
        validEventIsOpen(event);
        // 티켓 예매 가능 시간이 아직 안지났는지
        validTicketingTime(event);
        // 재고가 충분히 있는지 ( 추후 티켓 발급하면서도 2차로 검증함 )
        validItemStockEnough(order, item);
        // 아이템 구매 가능 횟수를 넘지 않는지.
        validItemPurchaseLimit(order, item);
    }

    /* 주문 거부 여부 검증 */
    public void validCanRefuse(Order order) {
        validAvailableRefundDate(order);
//        validStatusCanRefuse(getOrderStatus(order));
//        validCanWithDraw(order);
    }

    /* 이벤트 오픈 여부 검증 */
    public void validEventIsOpen(Event event) {
        event.validateNotOpenStatus();
    }

    /* 티켓팅 가능 시간인지 검증 */
    public void validTicketingTime(Event event) {
        event.validateTicketingTime();
    }

    /* 재고 여부 검증 */
    public void validItemStockEnough(Order order, TicketItem item) {
        item.validEnoughQuantity(order.getTotalQuantity());
    }

    public void validItemKindIsOneType(Order order) {
        List<Long> itemIds = order.getDistinctItemIds();
        if (itemIds.size() != 1) {
            throw OrdeItemNotOneTypeException.EXCEPTION;
        }
    }

    /* 주문 수량이 최소 구매 수량과 일치하는지 검증 */
    public void validItemPurchaseLimit(Order order, TicketItem item) {
        Long paidTicketCount = commonIssuedTicketService.countPaidTicket(order.getUserId(), item.getId());
        Long totalIssuedCount = paidTicketCount + order.getTotalQuantity();
        item.validPurchaseLimit(totalIssuedCount);
    }

    /* 주문 방식이 승인 주문인지 검증 */
    public void validMethodIsCanApprove(Order order) {
        if (isMethodPayment(order)) {
            throw NotApprovalOrderException.EXCEPTION;
        }
    }

    /* 주문 상태가 승인방식의 승인 가능한 상태인지 검증. */
    public void validStatusCanApprove(OrderStatus orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatus.PENDING_APPROVE)) {
            throw NotPendingOrderException.EXCEPTION;
        }
    }


    public void validUserNotDeleted(Order order) {
        Member member = commonMemberService.queryMember(order.getUserId());
        if (member.isDeletedUser()) {
            throw CanNotApproveDeletedUserOrderException.EXCEPTION;
        }
    }

    public void validMethodIsPaymentOrder(Order order) {
        if (!isMethodPayment(order)) {
            throw NotPaymentOrderException.EXCEPTION;
        }
    }

    public void validAvailableRefundDate(Order order) {
        if (!isRefundDateNotPassed(order)) {
            throw NotRefundAvailableDateOrderException.EXCEPTION;
        }
    }

    public void validStatusCanPaymentConfirm(OrderStatus orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatus.PENDING_PAYMENT)) {
            throw NotPendingOrderException.EXCEPTION;
        }
    }

    /* 무료 주문 여부. */
    public void validAmountIsFree(Order order) {
        if (order.isNeedPaid()) {
            throw NotFreeOrderException.EXCEPTION;
        }
    }

    /* 사용자가 주문자와 일치하는지 검증 */
    public void validOwner(Order order, Long currentUserId) {
        if (!order.getUserId().equals(currentUserId)) {
            throw NotOwnerOrderException.EXCEPTION;
        }
    }

    private Event getEvent(Order order) {
        Long itemGroupId = order.getItemGroupId();
        return commonEventService.findById(itemGroupId);
    }

    private TicketItem getItem(Order order) {
        Long itemId = order.getItemId();
        return commonTicketItemService.queryTicketItem(itemId);
    }

    private Boolean isMethodPayment(Order order) {
        System.out.println("is method payment");
        Boolean bool = order.getOrderMethod().isPayment();
        System.out.println(bool);
        return bool;
    }

    private OrderStatus getOrderStatus(Order order) {
        return order.getOrderStatus();
    }

    public Boolean isRefundDateNotPassed(Order order) {
        List<Event> events = commonEventService.findAllByIds(getEventIds(order));
        return true;
//        return reduceEventRefundAvailable(events);
    }

    private List<Long> getEventIds(Order order) {
        return order.getOrderItems().stream()
                .map(orderLineItem -> orderLineItem.getOrderItem().getItemGroupId())
                .toList();
    }

//    private Boolean reduceEventRefundAvailable(List<Event> events) {
//        return events.stream()
//                .map(Event::isRefundDateNotPassed)
//                .reduce(Boolean.TRUE, (Boolean::logicalAnd));
//    }
}
