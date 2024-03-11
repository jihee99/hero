package com.ex.hero.ticket.service;

import com.ex.hero.ticket.model.TicketItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketItemService {
    private final CommonTicketItemService commonTicketItemService;

    public TicketItem createTicketItem(TicketItem ticketItem) {
        ticketItem.validateTicketPayType();
        return commonTicketItemService.save(ticketItem);
    }

    public void deleteTicketItem(Long eventId, Long ticketItemId) {

        TicketItem ticketItem = commonTicketItemService.queryTicketItem(ticketItemId);
        // 해당 eventId에 속해 있는 티켓 아이템이 맞는지 확인
        ticketItem.validateEventId(eventId);
        ticketItem.deleteTicketItem();
        commonTicketItemService.save(ticketItem);
    }
}
