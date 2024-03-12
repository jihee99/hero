package com.ex.hero.order.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;
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

    public void validCanApproveOrder(Order order) {
        validMethodIsCanApprove(order);
        validStatusCanApprove(getOrderStatus(order));
        validCanDone(order);
        // 유저가 탈퇴를 안했는지 확인.
        validUserNotDeleted(order);
    }

    public void validCanConfirmPayment(Order order) {
        // 주문 방식이 결제 방식인지
        validMethodIsPaymentOrder(order);
        // 주문 상태가 결제 방식의 승인 상태인지
        validStatusCanPaymentConfirm(getOrderStatus(order));
        // 주문을 완료할 수 있는지
        validCanDone(order);
    }

    public void validCanFreeConfirm(Order order) {
        // 무료인지
        validAmountIsFree(order);
        // 주문 상태가 결제 방식의 승인 인지
        validStatusCanPaymentConfirm(getOrderStatus(order));
        validCanDone(order);
    }

    
    public void validEventIsOpen(Event event) {
        event.validateNotOpenStatus();
    }

    public void validTicketingTime(Event event) {
        event.validateTicketingTime();
    }

    public void validItemStockEnough(Order order, TicketItem item) {
        item.validEnoughQuantity(order.getTotalQuantity());
    }

    public void validItemKindIsOneType(Order order) {
        List<Long> itemIds = order.getDistinctItemIds();
        if (itemIds.size() != 1) {
            throw new RuntimeException();
            //TODO Exception Handling
            //OrdeItemNotOneTypeException.EXCEPTION
        }
    }

    public void validItemPurchaseLimit(Order order, TicketItem item) {
        Long paidTicketCount = commonIssuedTicketService.countPaidTicket(order.getUserId(), item.getId());
        Long totalIssuedCount = paidTicketCount + order.getTotalQuantity();
        item.validPurchaseLimit(totalIssuedCount);
    }

    public void validMethodIsCanApprove(Order order) {
        if (isMethodPayment(order)) {
            throw new RuntimeException();
            //TODO Exception Handling
            //NotApprovalOrderException.EXCEPTION
        }
    }

    public void validStatusCanApprove(OrderStatus orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatus.PENDING_APPROVE)) {
            throw new RuntimeException();
//             TODO ExceptionHandling
//            throw NotPendingOrderException.EXCEPTION;
        }
    }

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

    public void validUserNotDeleted(Order order) {
        Member member = commonMemberService.queryMember(order.getUserId());
        if (member.isDeletedUser()) {
            throw new RuntimeException();
            //CanNotApproveDeletedUserOrderException.EXCEPTION
        }
    }

    public void validMethodIsPaymentOrder(Order order) {
        if (!isMethodPayment(order)) {
            throw new RuntimeException();
            //NotPaymentOrderException.EXCEPTION;
        }
    }

    public void validStatusCanPaymentConfirm(OrderStatus orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatus.PENDING_PAYMENT)) {
            throw new RuntimeException();
//            NotPendingOrderException.EXCEPTION;
        }
    }

    public void validAmountIsFree(Order order) {
        if (order.isNeedPaid()) {
            throw new RuntimeException();
            //NotFreeOrderException.EXCEPTION
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
        return order.getOrderMethod().isPayment();
    }

    private OrderStatus getOrderStatus(Order order) {
        return order.getOrderStatus();
    }


}
