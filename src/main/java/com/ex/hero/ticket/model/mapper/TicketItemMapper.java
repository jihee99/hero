package com.ex.hero.ticket.model.mapper;

import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.common.vo.Money;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.dto.request.CreateTicketItemRequest;
import com.ex.hero.ticket.model.dto.request.GetEventTicketItemsResponse;
import com.ex.hero.ticket.model.dto.response.TicketItemResponse;
import com.ex.hero.ticket.service.CommonTicketItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Mapper
@RequiredArgsConstructor
public class TicketItemMapper {

    private final CommonTicketItemService commonTicketItemService;

    private final CommonEventService commonEventService;
    public TicketItem toTicketItem(CreateTicketItemRequest createTicketItemRequest, Long eventId) {

        return TicketItem.builder()
                .payType(createTicketItemRequest.getPayType())
                .name(createTicketItemRequest.getName())
                .description(createTicketItemRequest.getDescription())
                .price(Money.wons(createTicketItemRequest.getPrice()))
                .quantity(createTicketItemRequest.getSupplyCount())
                .supplyCount(createTicketItemRequest.getSupplyCount())
                .purchaseLimit(createTicketItemRequest.getPurchaseLimit())
                .type(createTicketItemRequest.getApproveType())
                .isSellable(true)
                .eventId(eventId)
                .build();
    }

    @Transactional(readOnly = true)
    public GetEventTicketItemsResponse toGetEventTicketItemsResponse(Long eventId, Boolean isAdmin) {

        Event event = commonEventService.findById(eventId);
        List<TicketItem> ticketItems = commonTicketItemService.findAllByEventId(event.getId());
        return GetEventTicketItemsResponse.from(
                ticketItems.stream()
                        .map(ticketItem -> TicketItemResponse.from(ticketItem, isAdmin))
                        .toList());
    }

//    @Transactional(readOnly = true)
//    public GetAppliedOptionGroupsResponse toGetAppliedOptionGroupsResponse(Long eventId) {
//
//        Event event = eventAdaptor.findById(eventId);
//        List<TicketItem> ticketItems = ticketItemAdaptor.findAllByEventId(event.getId());
//        List<AppliedOptionGroupResponse> appliedOptionGroups = new ArrayList<>();
//        ticketItems.forEach(
//                ticketItem ->
//                        appliedOptionGroups.add(
//                                AppliedOptionGroupResponse.from(
//                                        ticketItem,
//                                        ticketItem.getItemOptionGroups().stream()
//                                                .map(ItemOptionGroup::getOptionGroup)
//                                                .toList()
//                                                .stream()
//                                                .map(OptionGroupResponse::from)
//                                                .toList())));
//        return GetAppliedOptionGroupsResponse.from(appliedOptionGroups);
//    }

}
