package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.mappers.EventMapper;
import com.bookease.bookease.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventServiceTest {


    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    private EventRequestDTO mockEventRequestDTO;

    private Organizer mockOrganizer;

    private Event mockEvent;

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


        Event mockEvent = new Event();
        mockEvent.setId("e1");
        mockEvent.setName("Mock");
        mockEvent.setDescription("Mock desc");
        mockEvent.setStartingDate(LocalDateTime.parse("2024-09-05T16:00:00"));
        mockEvent.setEndingDate(LocalDateTime.parse("2024-10-05T16:00:00"));
        mockEvent.setCapacity(100);
        mockEvent.setAddress("101 Code Street");
        mockEvent.setCity("Sorocaba");
        mockEvent.setState("SP");
        mockEvent.setOrganizer(mockOrganizer);

        this.mockEvent = mockEvent;


    }

    @Test
    void getEventById() {
    }

    @Test
    void getAllEvents() {
    }

    @Test
    @DisplayName("Should create event successfully and return the right event")
    void createEvent() {

        Mockito.when(eventMapper.toEntity(mockEventRequestDTO, mockOrganizer)).thenReturn(mockEvent);

        Mockito.when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        Event result = eventService.createEvent(mockEventRequestDTO, mockOrganizer);

        assertNotNull(result);
        assertEquals(result.getId(), mockEvent.getId());
        assertEquals(mockEvent.getName(), result.getName());
        assertEquals(mockEvent.getDescription(), result.getDescription());
        assertEquals(mockEvent.getOrganizer(), result.getOrganizer());

        Mockito.verify(eventMapper, Mockito.times(1)).toEntity(mockEventRequestDTO, mockOrganizer);
        Mockito.verify(eventRepository, Mockito.times(1)).save(mockEvent);

    }

    @Test
    void saveEvent() {
    }

    @Test
    void deleteEvent() {
    }
}