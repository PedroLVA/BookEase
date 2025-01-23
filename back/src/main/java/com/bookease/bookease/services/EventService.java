package com.bookease.bookease.services;
import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Image;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.category.CategoryResponseDTO;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.mappers.EventMapper;
import com.bookease.bookease.dtos.user.UserEventResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.specification.EventSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;


@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    public Optional<Event> getEventById(String id){

        return eventRepository.findById(id);
    }

    public ResponseEntity<List<EventGetResponseDTO>> getAllEvents(Integer minCapacity, String categories){
        try {
            Specification<Event> spec = Specification.where(null);

            if (categories != null) {
                spec = spec.and(EventSpecifications.hasCategories(categories));
            }
            if (minCapacity != null) {
                spec = spec.and(EventSpecifications.hasCapacityGreaterThanOrEqualTo(minCapacity));
            }

            List<Event> events = eventRepository.findAll(spec);

            return ResponseEntity.ok(eventMapper.toEventResponseDTO(events));
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public Event createEvent(EventRequestDTO request, Organizer organizer){

        return eventRepository.save(eventMapper.toEntity(request, organizer));
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }


    @Transactional
    public void deleteEvent(String eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException("Event with ID " + eventId + " does not exist");
        }
        eventRepository.deleteById(eventId);
    }

}
