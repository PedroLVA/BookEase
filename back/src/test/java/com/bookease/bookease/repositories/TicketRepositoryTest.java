package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DataJpaTest
@ActiveProfiles("test")
class TicketRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TicketRepository ticketRepository;

    private Event event;
    private User user;

    @BeforeEach
    void setUp() {
        Organizer organizer = new Organizer();
        organizer.setName("Rock Organizer");
        entityManager.persist(organizer);

        user = new User();
        user.setName("Teste");
        user.setEmail("Teste@gmail.com");
        user.setPassword("Teste");
        user.setPhoneNumber("+1234567890");
        user.setDateOfBirth(LocalDateTime.now());
        user.setRole(Role.USER);

        entityManager.persist(user);

        event = new Event();
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
        entityManager.persist(event);

        // Step 3: Create and Persist Tickets
        Ticket ticket1 = new Ticket();
        ticket1.setEvent(event);
        ticket1.setSeatNumber("Teste");
        ticket1.setTicketType("Teste");
        ticket1.setTicketPrice(100.0);
        ticket1.setValidUntil(LocalDateTime.now().plusHours(1));
        ticket1.setUser(user);

        Ticket ticket2 = new Ticket();
        ticket2.setEvent(event);  // Fixed: was setting to ticket1 instead of ticket2
        ticket2.setSeatNumber("Teste2");  // Fixed: was setting to ticket1
        ticket2.setTicketType("Teste2");  // Fixed: was setting to ticket1
        ticket2.setTicketPrice(100.0);    // Fixed: was setting to ticket1
        ticket2.setValidUntil(LocalDateTime.now().plusHours(1));
        ticket2.setUser(user);// Fixed: was setting to ticket1

        entityManager.persist(ticket1);
        entityManager.persist(ticket2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should return correct number of tickets booked to a certain event")
    void countByEventIdSuccess() {
        long bookedTickets = ticketRepository.countByEventId(event.getId());
        assertThat(bookedTickets).isEqualTo(2);
    }

    @Test
    @DisplayName("Should fail when expecting incorrect ticket count")
    void countByEventIdError() {
        entityManager.persist(this.createNewTicket());

        long bookedTickets = ticketRepository.countByEventId(event.getId());

        assertThat(bookedTickets).isNotEqualTo(2);
    }

    @Test
    @DisplayName("Should return updated count when new ticket is added")
    void countByEventIdWithNewTicket() {
        // Arrange

        entityManager.persist(this.createNewTicket());

        // Act
        long bookedTickets = ticketRepository.countByEventId(event.getId());

        // Assert
        assertThat(bookedTickets).isEqualTo(3); // Should be 3 since we added a third ticket
    }

    private Ticket createNewTicket(){
        Ticket ticket3 = new Ticket();
        ticket3.setEvent(event);
        ticket3.setSeatNumber("Teste3");
        ticket3.setTicketType("Teste3");
        ticket3.setTicketPrice(100.0);
        ticket3.setValidUntil(LocalDateTime.now().plusHours(1));
        ticket3.setUser(user);

        return ticket3;
    }

}