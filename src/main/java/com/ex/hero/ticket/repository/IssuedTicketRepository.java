package com.ex.hero.ticket.repository;

import java.util.List;

import com.ex.hero.ticket.model.IssuedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, Long>  {

	Boolean existsByEventId(Long eventId);

    @Query("SELECT COUNT(i) FROM tbl_issued_ticket i WHERE i.userInfo.userId = :userId AND i.itemInfo.ticketItemId = :issuedTicketId AND i.issuedTicketStatus = 'PAID'")
    Long countPaidTickets(@Param("userId") Long userId, @Param("issuedTicketId") Long issuedTicketId);

	List<IssuedTicket> findAllByOrderId(Long orderId);

}
