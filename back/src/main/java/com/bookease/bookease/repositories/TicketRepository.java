package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    long countByEventId(UUID eventId);
}
