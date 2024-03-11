package com.ex.hero.ticket.service;

import com.ex.hero.ticket.model.dto.request.GetEventTicketItemsResponse;
import com.ex.hero.ticket.model.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTicketItemUseCase {

    private final TicketItemMapper ticketItemMapper;
    private final TicketItemService ticketItemService;

    public GetEventTicketItemsResponse execute(Long eventId, Long ticketItemId) {

        ticketItemService.deleteTicketItem(eventId, ticketItemId);

        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, true);
    }
}
