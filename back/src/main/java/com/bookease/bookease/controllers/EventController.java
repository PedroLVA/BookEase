package com.bookease.bookease.controllers;
import com.bookease.bookease.domain.Category;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.services.CategoryService;
import com.bookease.bookease.services.EventService;
import com.bookease.bookease.services.OrganizerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final OrganizerService organizerService;
    private final EventService eventService;
    private final CategoryService categoryService;


    @PostMapping()
    public ResponseEntity<?> createEvent(@RequestBody @Validated EventRequestDTO eventRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        Optional<Organizer> optionalOrganizer = organizerService.getOrganizerByEmail(loggedInUserEmail);
        if (optionalOrganizer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organizer not found.");
        }

        Organizer organizer = optionalOrganizer.get();
        if (organizer.getRole() != Role.ORGANIZER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only organizers can create events.");
        }

        Event event = eventService.createEvent(eventRequestDTO, organizer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @Transactional
    @PostMapping("/{eventId}/categories")
    public ResponseEntity<?> addCategoriesToEvent(@PathVariable String eventId,
                                                  @RequestBody List<String> categoryIds){

        Optional<Event> optionalEvent = eventService.getEventById(eventId);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        Event event = optionalEvent.get();

        List<Category> categories = categoryService.getAllCategoriesById(categoryIds);
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid categories found");
        }

        for (Category category : categories) {
            event.addCategory(category); // Use a helper method
            category.addEvent(event);   // Ensure bidirectional consistency
        }

        eventService.saveEvent(event);

        return ResponseEntity.ok("Categories added successfully");

    }

    @GetMapping()
    public ResponseEntity<List<EventGetResponseDTO>> getAllEvents( @RequestParam(required = false)  String categories,
                                                                   @RequestParam(required = false) Integer minCapacity){
        return eventService.getAllEvents(minCapacity, categories);
    }



}
