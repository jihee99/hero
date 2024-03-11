package com.ex.hero.ticket.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.service.CommonGroupService;
import com.ex.hero.ticket.model.dto.request.CreateTicketItemRequest;
import com.ex.hero.ticket.model.dto.response.TicketItemResponse;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketItemUseCase {
    private final TicketItemService ticketItemService;
    private final TicketItemMapper ticketItemMapper;

    public TicketItemResponse execute(CreateTicketItemRequest createTicketItemRequest, Long eventId) {
//        Event event = commonEventService.findById(eventId);
//        Group group = commonGroupService.findById(event.getGroupId());

        TicketItem ticketItem = ticketItemService.createTicketItem(ticketItemMapper.toTicketItem(createTicketItemRequest, eventId));
        return TicketItemResponse.from(ticketItem, true);
    }
}
