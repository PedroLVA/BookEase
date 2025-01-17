package com.bookease.bookease.services;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Ticket;
import com.bookease.bookease.dtos.mappers.TicketMapper;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
import com.bookease.bookease.exceptions.EventFullException;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Set;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
        private final TicketRepository ticketRepository;
        private final EventRepository eventRepository;
        private final TicketMapper ticketMapper;

        public TicketResponseDTO createTicket(TicketRequestDTO ticketData ){

            Event event = eventRepository.findById(ticketData.eventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with ID: " + ticketData.eventId()));

            long bookedTickets = ticketRepository.countByEventId(ticketData.eventId());

            if(bookedTickets >= event.getCapacity()){
                throw new EventFullException("Event is already full.");
            }

            Ticket savedTicket = ticketRepository.save(ticketMapper.toEntity(ticketData));
            return ticketMapper.toTicketResponseDTO(savedTicket);

        }


    public Set<TicketResponseDTO> getAllTickets(){
        return ticketRepository.findAll().stream().map(ticketMapper::toTicketResponseDTO).collect(Collectors.toSet());
    }

    public void deleteTicket(String ticketId){
        if(!ticketRepository.existsById(ticketId)){
            throw new EntityNotFoundException("Ticket with id: " + ticketId + " coundn't be found");
        }
        ticketRepository.deleteById(ticketId);
    }
}
