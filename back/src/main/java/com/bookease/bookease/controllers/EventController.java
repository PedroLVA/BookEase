package com.bookease.bookease.controllers;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.services.EventService;
import com.bookease.bookease.services.OrganizerService;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final UserService userService;
    private final OrganizerService organizerService;
    private final EventService eventService;


    @PostMapping()
    public ResponseEntity<?> createEvent(@RequestBody @Validated EventRequestDTO eventRequestDTO) {
        // Get the currently logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        // Retrieve the user from the database
        Organizer organizer =  organizerService.getUserByEmail(loggedInUserEmail);

        // Ensure the user has the role of ORGANIZER
        if (organizer.getRole() != Role.ORGANIZER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only organizers can create events.");
        }

        // Create the event
        Event event = eventService.createEvent(eventRequestDTO, organizer);
        return ResponseEntity.ok().build();
    }


    @GetMapping()
    public ResponseEntity<List<EventGetResponseDTO>> getAllEvents(){
        return eventService.getAllEvents();
    }


}
