package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    @PostMapping
    public void post(EventRequestDTO request, Organizer organizer){
        Event event = createEvent(request, organizer);
        this.eventRepository.save(event);
    }

    private Event createEvent(EventRequestDTO request, Organizer organizer){
        Event event = new Event();

        event.setName(request.name());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setAddress(request.address());
        event.setCity(request.city());
        event.setState(request.state());
        event.setHomeNumber(request.homeNumber());
        event.setOrganizer(organizer);

        return event;
    }

}
