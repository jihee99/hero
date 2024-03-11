package com.ex.hero.ticket.service;

import com.ex.hero.ticket.model.dto.request.GetEventTicketItemsResponse;
import com.ex.hero.ticket.model.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventTicketItemsUseCase {

    private final TicketItemMapper ticketItemMapper;

    public GetEventTicketItemsResponse execute(Long eventId) {
        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, false);
    }

    public GetEventTicketItemsResponse executeForManager(Long eventId) {
        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, true);
    }
}