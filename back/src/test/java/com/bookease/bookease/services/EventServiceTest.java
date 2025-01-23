package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.event.EventGetResponseDTO;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.mappers.EventMapper;
import com.bookease.bookease.repositories.EventRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @DisplayName("Should return the right event given the id")
    void getEventById() {
        Mockito.when(eventRepository.findById(mockEvent.getId())).thenReturn(Optional.of(mockEvent));

        Optional<Event> result = eventService.getEventById(mockEvent.getId());

        AssertionsForClassTypes.assertThat(result)
                .isPresent() // verifies Optional contains a value
                .hasValueSatisfying(event -> {
                    AssertionsForClassTypes.assertThat(event.getName()).isEqualTo("Mock");
                    AssertionsForClassTypes.assertThat(event.getDescription()).isEqualTo("Mock desc");
                    AssertionsForClassTypes.assertThat(event.getStartingDate()).isEqualTo(LocalDateTime.parse("2024-09-05T16:00:00"));
                });

        Mockito.verify(eventRepository, Mockito.times(1)).findById(mockEvent.getId());


    }

    @Test
    @DisplayName("Should return all events, since category and minCapacity are null")
    void getAllEventsCaseCategoryAndMinCapacityNull() {

        Event mockEvent2 = new Event();
        mockEvent2.setId("e2");
        mockEvent2.setName("Mock2");
        mockEvent2.setDescription("Mock des2c");
        mockEvent2.setStartingDate(LocalDateTime.parse("2024-09-05T16:00:00"));
        mockEvent2.setEndingDate(LocalDateTime.parse("2024-10-05T16:00:00"));
        mockEvent2.setCapacity(100);
        mockEvent2.setAddress("101 Code Street");
        mockEvent2.setCity("Sorocaba");
        mockEvent2.setState("SP");
        mockEvent2.setOrganizer(mockOrganizer);

        Specification<Event> spec = Specification.where(null);

        List<Event> mockEventList = List.of(mockEvent, mockEvent2);

        List<EventGetResponseDTO> eventDTOs = mockEventList.stream()
                .map(event -> new EventGetResponseDTO(
                        event.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getStartingDate(),
                        event.getEndingDate(),
                        true, // Assuming event is active by default
                        LocalDateTime.now(), // Assuming publishing date is now, change if necessary
                        event.getAddress(),
                        event.getCity(),
                        event.getState(),
                        "", // Assuming homeNumber is empty, update as needed
                        event.getCapacity(),
                        event.getOrganizer().getId(),
                        new HashSet<>(), // Assuming no ticket IDs, update as needed
                        new HashSet<>(), // Assuming no attendees, update as needed
                        new HashSet<>(), // Assuming no categories, update as needed
                        new HashSet<>()  // Assuming no images, update as needed
                ))
                .collect(Collectors.toList());

        // Mock repository response
        Mockito.when(eventRepository.findAll(Mockito.any(Specification.class))).thenReturn(mockEventList);

        // Mock mapper response
        Mockito.when(eventMapper.toEventResponseDTO(mockEventList)).thenReturn(eventDTOs);

        // Call the service
        ResponseEntity<List<EventGetResponseDTO>> response = eventService.getAllEvents(null, null);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventDTOs.size(), response.getBody().size());
        assertEquals(eventDTOs.get(0).id(), response.getBody().get(0).id());


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