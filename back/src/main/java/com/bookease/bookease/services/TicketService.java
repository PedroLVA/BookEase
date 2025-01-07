package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Ticket;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.mappers.TicketMapper;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
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

    public TicketResponseDTO createTicket(TicketRequestDTO ticketData ){
        User user = userService.getCurrentuser();

        Event event = eventRepository.findById(ticketData.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + ticketData.eventId()));


        Ticket savedTicket = ticketRepository.save(TicketMapper.toEntity(ticketData, event, user));
        return TicketMapper.toTicketResponseDTO(savedTicket);

    }
}
