package com.bookease.bookease.services;
import com.bookease.bookease.domain.*;
import com.bookease.bookease.dtos.mappers.TicketMapper;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import com.bookease.bookease.dtos.ticket.TicketResponseDTO;
import com.bookease.bookease.exceptions.EventFullException;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService;


    private String eventId = "";
    private Event event;
    private TicketResponseDTO ticketResponseDTO;
    private TicketRequestDTO ticketRequestDTO;
    private Ticket ticket;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);

        eventId = "50716b4c-c5cf-4d17-bb5b-e1fa70a3010c";

        ticketRequestDTO = this.createTestTicketRequest();

        event = this.createTestEvent();

        ticket = this.createTestTicket();

        ticketResponseDTO = this.createTestTicketResponseDTO();

    }

    @Test
    @DisplayName("Should create ticket successfully")
    void createTicketCase1() {

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(ticketRepository.countByEventId(eventId)).thenReturn(50l);
        when(ticketMapper.toEntity(ticketRequestDTO)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toTicketResponseDTO(ticket)).thenReturn(ticketResponseDTO);

        TicketResponseDTO result = ticketService.createTicket(ticketRequestDTO);

        assertThat(result).isNotNull()
                .isEqualTo(ticketResponseDTO);

        verify(eventRepository).findById(eventId);
        verify(ticketRepository).countByEventId(eventId);
        verify(ticketMapper).toEntity(ticketRequestDTO);
        verify(ticketRepository).save(ticket);
        verify(ticketMapper).toTicketResponseDTO(ticket);

    }

    @Test
    @DisplayName("Should throw entity not found exception when trying to create ticket")
    void createTicketCase2() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.createTicket(ticketRequestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Event not found with ID: " + eventId);

        verify(eventRepository).findById(eventId);

        verify(ticketRepository, never()).countByEventId(anyString());
        verify(ticketMapper, never()).toEntity(any());
        verify(ticketRepository, never()).save(any());
        verify(ticketMapper, never()).toTicketResponseDTO(any());
    }

    @Test
    @DisplayName("Should throw eventFullException when trying to create ticket")
    void createTicketCase3() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(ticketRepository.countByEventId(eventId)).thenReturn(100l);


        assertThatThrownBy(() -> ticketService.createTicket(ticketRequestDTO))
                .isInstanceOf(EventFullException.class)
                .hasMessage("Event is already full.");

        verify(eventRepository).findById(eventId);
        verify(ticketRepository).countByEventId(eventId);


        verify(ticketMapper, never()).toEntity(any());
        verify(ticketRepository, never()).save(any());
        verify(ticketMapper, never()).toTicketResponseDTO(any());


    }


    @Test
    @DisplayName("Should be able to delete ticket successfully")
    void deleteTicketCase1() {

        String ticketId = "test-ticket-id";
        when(ticketRepository.existsById(ticketId)).thenReturn(true);

        ticketService.deleteTicket(ticketId);

        verify(ticketRepository).existsById(ticketId);
        verify(ticketRepository).deleteById(ticketId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when trying to delete ticket")
    void deleteTicketCase2() {

        String ticketId = "test-ticket-id";
        when(ticketRepository.existsById(ticketId)).thenReturn(false);

        assertThatThrownBy(() -> ticketService.deleteTicket(ticketId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Ticket with id: " + ticketId + " coundn't be found");

        verify(ticketRepository).existsById(ticketId);

        verify(ticketRepository, never()).deleteById(any());

    }

    private Ticket createTestTicket(){
        ticket = new Ticket();
        ticket.setId("random-ticket-id");
        ticket.setEvent(event);
        ticket.setSeatNumber("10");
        ticket.setTicketType("VIP");
        ticket.setTicketPrice(100.00);
        ticket.setPaymentStatus(Ticket.PaymentStatus.PENDING);
        ticket.setValidUntil(LocalDateTime.now().plusHours(1));
        ticket.setUser(this.createTestUser());

        return ticket;

    }

    private Event createTestEvent(){
        Organizer organizer = new Organizer();
        organizer.setId("53716b4c-c5cf-4d17-bb5b-e1fa70a3010c");
        organizer.setName("Rock Organizer");
        organizer.setEmail("admin.ig@example.com");
        organizer.setPassword("Rockasa");
        organizer.setPhoneNumber("+1234567890");
        organizer.setDateOfBirth(LocalDateTime.now());
        organizer.setRole(Role.ORGANIZER);

        Event event = new Event();
        event.setId(eventId);
        event.setName("Evento de rock");
        event.setDescription("Show de rock pesado.");
        event.setStartingDate(LocalDateTime.of(2024, 9, 5, 16, 0));
        event.setEndingDate(LocalDateTime.of(2024, 9, 5, 22, 0));
        event.setCapacity(100);
        event.setAddress("101 Code Street");
        event.setCity("Austin");
        event.setState("TX");
        event.setHomeNumber("3D");
        event.setOrganizer(organizer);

        return event;
    }

    private TicketRequestDTO createTestTicketRequest() {
        return new TicketRequestDTO(
                LocalDateTime.now().plusDays(1),
                100.0,
                "A1",
                "VIP",
                LocalDateTime.now().plusDays(1),
                eventId
        );
    }

    private TicketResponseDTO createTestTicketResponseDTO(){
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getBookingDate(),
                ticket.getValidUntil(),
                ticket.getPaymentStatus().name(),
                ticket.getTicketPrice(),
                ticket.getSeatNumber(),
                ticket.getTicketType(),
                new TicketResponseDTO.EventSummary(
                        ticket.getEvent().getId(),
                        ticket.getEvent().getName(),
                        ticket.getEvent().getDescription(),
                        ticket.getEvent().getStartingDate(),
                        ticket.getEvent().getEndingDate(),
                        ticket.getEvent().getAddress(),
                        ticket.getEvent().getCity(),
                        ticket.getEvent().getState(),
                        ticket.getEvent().getHomeNumber()
                ),
                new TicketResponseDTO.UserSummary(
                        ticket.getUser().getId(),
                        ticket.getUser().getName(),
                        ticket.getUser().getEmail()
                )
        );
    }

    private User createTestUser(){
        User user = new User();
        user.setId("53736b4c-c5cf-4d17-bb5b-e1fa70a3010c");
        user.setName("user");
        user.setEmail("user.ig@example.com");
        user.setPassword("Rockasa");
        user.setPhoneNumber("+1234567890");
        user.setDateOfBirth(LocalDateTime.now());
        user.setRole(Role.USER);

        return user;

    }
}