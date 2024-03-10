package com.ex.hero.ticket.service;

import com.ex.hero.ticket.model.IssuedTicket;
import com.ex.hero.ticket.repository.IssuedTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonIssuedTicketService {

    private final IssuedTicketRepository issuedTicketRepository;

    public IssuedTicket save(IssuedTicket issuedTicket) {
        return issuedTicketRepository.save(issuedTicket);
    }

    public Boolean existsByEventId(Long eventId) {
        return issuedTicketRepository.existsByEventId(eventId);
    }
}
