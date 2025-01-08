package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
import com.bookease.bookease.services.TicketService;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;


    @PostMapping()
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody @Validated TicketRequestDTO ticketRequestDTO){

        return ResponseEntity.ok(ticketService.createTicket(ticketRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<Set<TicketResponseDTO>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
}
