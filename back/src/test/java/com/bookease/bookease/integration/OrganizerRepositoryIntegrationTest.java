package com.bookease.bookease.integration;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.user.RegisterDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrganizerRepositoryIntegrationTest {

    @Autowired
    OrganizerRepository organizerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setup() {

        // Clean up the database before each test
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        organizerRepository.deleteAll();
    }

    private Organizer createTestOrganizer(String email) {
        RegisterDTO registerDTO = new RegisterDTO(
                "Test Organizer",
                email,
                "password123",
                "1234567890",
                LocalDateTime.of(1990, 1, 1, 0, 0),
                Role.ORGANIZER
        );
        return new Organizer(registerDTO, "encodedPassword");
    }


    @Test
    public void findByEmail_OptionalOrganizerIsPresent(){
        //arrange
        Organizer organizer1 = createTestOrganizer("example@gmail.com");
        organizerRepository.save(organizer1);

        //act

        Optional<Organizer> returnedOrganizer = organizerRepository.findByEmail(organizer1.getEmail());

        //assertion

        assertThat(returnedOrganizer).isPresent();
        assertThat(returnedOrganizer.get().getEmail()).isEqualTo(organizer1.getEmail());

    }

    @Test
    public void findByEmail_returnsEmptyOptional_whenOrganizerDoesNotExist() {
        // Act
        Optional<Organizer> result = organizerRepository.findByEmail("nonexisting@gmail.com");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void findByEmail_returnsEmptyOptional_forEmptyString() {
        assertThat(organizerRepository.findByEmail("")).isEmpty();
    }

    @Test
    public void findByEmail_returnsEmptyOptional_forNull() {
        assertThat(organizerRepository.findByEmail(null)).isEmpty();
    }





}
