package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.category.CategoryResponseDTO;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.image.ImageResponseDTO;
import com.bookease.bookease.dtos.user.UserEventResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public ResponseEntity<List<EventGetResponseDTO>> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventGetResponseDTO> eventDtos = events.stream().map(event -> new EventGetResponseDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.isActive(),
                event.getPublishingDate(),
                event.getAddress(),
                event.getCity(),
                event.getState(),
                event.getHomeNumber(),
                event.getOrganizer().getId(),
                event.getAttendees().stream()
                        .map(attendee -> new UserEventResponseDTO(attendee.getId(), attendee.getName(), attendee.getEmail()))
                        .collect(Collectors.toSet()),
                event.getCategories().stream()
                        .map(category -> new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription()))
                        .collect(Collectors.toSet()),
                event.getImages().stream()
                        .map(image -> new ImageResponseDTO(image.getId(), image.getType()))
                        .collect(Collectors.toSet())
        )).collect(Collectors.toList());

        return ResponseEntity.ok(eventDtos);
    }


    public Event createEvent(EventRequestDTO request, Organizer organizer){
        Event event = new Event();
        event.setName(request.name());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setAddress(request.address());
        event.setCity(request.city());
        event.setActive(true);
        event.setState(request.state());
        event.setPublishingDate(LocalDateTime.now());
        event.setHomeNumber(request.homeNumber());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

}
