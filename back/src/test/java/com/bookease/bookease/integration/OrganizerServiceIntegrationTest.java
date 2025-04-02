package com.bookease.bookease.integration;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.RegisterDTO;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.TicketRepository;
import com.bookease.bookease.services.OrganizerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrganizerServiceIntegrationTest {
    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

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
    public void getOrganizerByEmail_ReturnsOrganizer_WhenOrganizerExists() {
        // Arrange
        String email = "test@example.com";
        Organizer organizer = createTestOrganizer(email);
        organizerRepository.save(organizer);

        // Act
        Optional<Organizer> foundOrganizer = organizerService.getOrganizerByEmail(email);

        // Assert
        assertThat(foundOrganizer).isPresent();
        assertThat(foundOrganizer.get().getEmail()).isEqualTo(email);
    }

    @Test
    public void getOrganizerByEmail_ReturnsEmpty_WhenOrganizerDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";

        // Act
        Optional<Organizer> foundOrganizer = organizerService.getOrganizerByEmail(email);

        // Assert
        assertThat(foundOrganizer).isEmpty();
    }

    @Test
    public void getAllOrganizers_ReturnsAllOrganizers() {
        // Arrange
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        organizerRepository.deleteAll();

        Organizer organizer1 = createTestOrganizer("organizer1@example.com");
        Organizer organizer2 = createTestOrganizer("organizer2@example.com");

        organizerRepository.save(organizer1);
        organizerRepository.save(organizer2);

        // Act
        List<UserGetResponseDTO> organizers = organizerService.getAllOrganizers();

        // Assert
        assertThat(organizers).hasSize(2);
        assertThat(organizers)
                .extracting(UserGetResponseDTO::email)
                .containsExactlyInAnyOrder("organizer1@example.com", "organizer2@example.com");
    }

    @Test
    public void getAllOrganizers_ReturnsEmptyList_WhenNoOrganizersExist() {
        // Arrange
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        organizerRepository.deleteAll();

        // Act
        List<UserGetResponseDTO> organizers = organizerService.getAllOrganizers();

        // Assert
        assertThat(organizers).isEmpty();
    }

    @Test
    public void registerNewOrganizer_SavesOrganizer() {
        // Arrange
        Organizer organizer = createTestOrganizer("neworganizer@example.com");

        // Act
        User savedUser = organizerService.registerNewOrganizer(organizer);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("neworganizer@example.com");


        Optional<Organizer> foundOrganizer = organizerRepository.findByEmail("neworganizer@example.com");
        assertThat(foundOrganizer).isPresent();
    }


}
