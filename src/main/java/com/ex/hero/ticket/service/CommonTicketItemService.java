package com.ex.hero.ticket.service;

import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketItemStatus;
import com.ex.hero.ticket.exception.TicketItemNotFoundException;
import com.ex.hero.ticket.repository.TicketItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonTicketItemService {

    private final TicketItemRepository ticketItemRepository;
    public TicketItem save(TicketItem ticketItem) {
        return ticketItemRepository.save(ticketItem);
    }

    public List<TicketItem> findAllByEventId(Long eventId) {
        return ticketItemRepository.findAllByEventIdAndTicketItemStatus(eventId, TicketItemStatus.VALID);
    }

    public TicketItem queryTicketItem(Long ticketItemId) {
        return ticketItemRepository
                .findByIdAndTicketItemStatus(ticketItemId, TicketItemStatus.VALID)
                .orElseThrow(() -> TicketItemNotFoundException.EXCEPTION);
    }

}
