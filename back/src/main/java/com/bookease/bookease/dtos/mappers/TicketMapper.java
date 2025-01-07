package com.bookease.bookease.dtos.mappers;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Ticket;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketMapper {


    public static Ticket toEntity(@NotNull TicketRequestDTO request, @NotNull Event event, @NotNull User user){
        Ticket ticket = new Ticket();
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

    public static TicketResponseDTO toTicketResponseDTO(Ticket ticket) {
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

