package com.ex.hero.ticket.repository;

import com.ex.hero.ticket.model.IssuedTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, Long>  {
    Boolean existsByEventId(Long eventId);

}
