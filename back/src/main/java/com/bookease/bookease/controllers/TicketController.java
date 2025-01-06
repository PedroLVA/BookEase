package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.services.TicketService;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;


    @PostMapping()
    public ResponseEntity createTicket(@RequestBody @Validated TicketRequestDTO ticketRequestDTO){

        return ResponseEntity.ok(ticketService.createTicket(ticketRequestDTO));
    }
}
