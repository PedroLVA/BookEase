package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, String> {

    long countByEventId(String eventId);
}
