package com.ex.hero.ticket.repository;

import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketItemRepository extends JpaRepository<TicketItem, Long> {

    List<TicketItem> findAllByEventIdAndTicketItemStatus(Long eventId, TicketItemStatus status);

    Boolean existsByEventId(Long eventId);
    Optional<TicketItem> findByIdAndTicketItemStatus(Long ticketItemId, TicketItemStatus status);

}
