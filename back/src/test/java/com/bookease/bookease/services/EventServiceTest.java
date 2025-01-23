package com.bookease.bookease.services;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.mappers.EventMapper;
import com.bookease.bookease.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class EventServiceTest {


    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    private EventRequestDTO mockEventRequestDTO;

    private Organizer mockOrganizer;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        //Setup
        //event request DTO

        LocalDateTime startDate = LocalDateTime.parse("2024-09-05T16:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2024-10-05T16:00:00");

        EventRequestDTO eventRequestDTO = new EventRequestDTO("Mock",
                "Mock desc",
                startDate,
                endDate,
                100,
                "101 Code Street",
                "Sorocaba",
                "SP",
                "10");

        mockEventRequestDTO = eventRequestDTO;

        Organizer organizer = new Organizer();
        //config inicial
        organizer.setId("53736b4c-c5cf-4d17-bb5b-e1fa70a3010c");
        organizer.setName("organizer");
        organizer.setEmail("organizer.ig@example.com");
        organizer.setPassword("Rockasa");
        organizer.setPhoneNumber("+1234567890");
        organizer.setDateOfBirth(LocalDateTime.now());
        organizer.setRole(Role.ORGANIZER);

        mockOrganizer = organizer;

    }

    @Test
    void getEventById() {
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void createEvent() {

    }

    @Test
    void saveEvent() {
    }

    @Test
    void deleteEvent() {
    }
}