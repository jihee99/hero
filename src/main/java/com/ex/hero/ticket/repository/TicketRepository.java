package com.ex.hero.ticket.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.ticket.model.TicketItem;

public interface TicketRepository extends JpaRepository<TicketItem, UUID> {

}
