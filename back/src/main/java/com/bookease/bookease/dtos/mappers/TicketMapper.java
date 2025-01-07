package com.bookease.bookease.dtos.mappers;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Ticket;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TicketMapper {
    private final EventRepository eventRepository;
    private final UserService userService;


    public Ticket toEntity(@NotNull TicketRequestDTO request){
        Ticket ticket = new Ticket();
        User user = userService.getCurrentuser();

        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + request.eventId()));
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setSeatNumber(request.seatNumber());
        ticket.setTicketType(request.ticketType());
        ticket.setTicketPrice(request.ticketPrice());
        ticket.setPaymentStatus(Ticket.PaymentStatus.PENDING);
        ticket.setValidUntil(request.validUntil() != null ? request.validUntil() : event.getEndingDate());
        return ticket;
    }

    public TicketResponseDTO toTicketResponseDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getBookingDate(),
                ticket.getValidUntil(),
                ticket.getPaymentStatus().name(),
                ticket.getTicketPrice(),
                ticket.getSeatNumber(),
                ticket.getTicketType(),
                new TicketResponseDTO.EventSummary(
                        ticket.getEvent().getId(),
                        ticket.getEvent().getName(),
                        ticket.getEvent().getDescription(),
                        ticket.getEvent().getStartingDate(),
                        ticket.getEvent().getEndingDate(),
                        ticket.getEvent().getAddress(),
                        ticket.getEvent().getCity(),
                        ticket.getEvent().getState(),
                        ticket.getEvent().getHomeNumber()
                ),
                new TicketResponseDTO.UserSummary(
                        ticket.getUser().getId(),
                        ticket.getUser().getName(),
                        ticket.getUser().getEmail()
                )
        );
    }
}

