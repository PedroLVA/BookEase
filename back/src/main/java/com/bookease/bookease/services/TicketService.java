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
    private final TicketMapper ticketMapper;

    public TicketResponseDTO createTicket(TicketRequestDTO ticketData ){



        Ticket savedTicket = ticketRepository.save(ticketMapper.toEntity(ticketData));
        return ticketMapper.toTicketResponseDTO(savedTicket);

    }
}
