package com.ex.hero.ticket.repository;

import com.ex.hero.ticket.model.TicketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketItem, Long> {

}
