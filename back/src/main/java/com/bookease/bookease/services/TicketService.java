package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Ticket;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final EventRepository eventRepository;

    public Ticket createTicket(TicketRequestDTO ticketData ){
        User user = userService.getCurrentuser();

        Event event = eventRepository.findById(ticketData.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + ticketData.eventId()));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setSeatNumber(ticketData.seatNumber());
        ticket.setTicketType(ticketData.ticketType());
        ticket.setTicketPrice(ticketData.ticketPrice());
        ticket.setPaymentStatus(Ticket.PaymentStatus.PENDING);
        ticket.setValidUntil(ticketData.validUntil() != null ? ticketData.validUntil() : event.getEndingDate());
        ticketRepository.save(ticket);
        return ticket;

    }
}
