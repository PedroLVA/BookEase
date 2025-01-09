package com.bookease.bookease.services;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Image;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.category.CategoryResponseDTO;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.user.UserEventResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public Optional<Event> getEventById(UUID id){
        return eventRepository.findById(id);
    }

    public ResponseEntity<List<EventGetResponseDTO>> getAllEvents(){
        try {
            List<Event> events = eventRepository.findAll();
            List<EventGetResponseDTO> eventDtos = events.stream().map(event -> new EventGetResponseDTO(
                    event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getStartingDate(),
                    event.getEndingDate(),
                    event.isActive(),
                    event.getPublishingDate(),
                    event.getAddress(),
                    event.getCity(),
                    event.getState(),
                    event.getHomeNumber(),
                    event.getCapacity(),
                    event.getOrganizer().getId(),
                    event.getAttendees().stream()
                            .map(attendee -> new UserEventResponseDTO(attendee.getId(), attendee.getName(), attendee.getEmail()))
                            .collect(Collectors.toSet()),
                    event.getCategories().stream()
                            .map(category -> new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription()))
                            .collect(Collectors.toSet()),
                    event.getImages().stream()
                            .map(Image::getId) // Map to UUID instead of a DTO
                            .collect(Collectors.toSet())
            )).collect(Collectors.toList());

            return ResponseEntity.ok(eventDtos);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            throw e;
        }

    }


    public Event createEvent(EventRequestDTO request, Organizer organizer){
        Event event = new Event();
        event.setName(request.name());
        event.setDescription(request.description());
        event.setStartingDate(request.startingDate());
        event.setEndingDate(request.endingDate());
        event.setAddress(request.address());
        event.setCapacity(request.capacity());
        event.setCity(request.city());
        event.setActive(true);
        event.setState(request.state());
        event.setPublishingDate(LocalDateTime.now());
        event.setHomeNumber(request.homeNumber());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }


    @Transactional
    public void deleteEvent(UUID eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException("Event with ID " + eventId + " does not exist");
        }
        eventRepository.deleteById(eventId);
    }

}
