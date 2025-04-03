package com.bookease.bookease.integration;

import com.bookease.bookease.controllers.OrganizerController;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.dtos.user.RegisterDTO;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.TicketRepository;
import com.bookease.bookease.services.OrganizerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrganizerControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

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

    // Use @WithMockUser to simulate an authenticated user
    @Test
    @WithMockUser(roles = {"ADMIN"}) // Adjust role as needed
    public void getAllUsers_ReturnsEmptyList_WhenNoOrganizersExist() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/organizer/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response
        String responseJson = result.getResponse().getContentAsString();
        List<UserGetResponseDTO> users = objectMapper.readValue(responseJson,
                new TypeReference<List<UserGetResponseDTO>>() {});

        // Assert
        assertThat(users).isEmpty();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"}) // Adjust role as needed
    public void getAllUsers_ReturnsAllOrganizers_WhenOrganizersExist() throws Exception {
        // Arrange
        Organizer organizer1 = createTestOrganizer("organizer1@example.com");
        Organizer organizer2 = createTestOrganizer("organizer2@example.com");

        organizerRepository.save(organizer1);
        organizerRepository.save(organizer2);

        // Act
        MvcResult result = mockMvc.perform(get("/organizer/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response
        String responseJson = result.getResponse().getContentAsString();
        List<UserGetResponseDTO> users = objectMapper.readValue(responseJson,
                new TypeReference<List<UserGetResponseDTO>>() {});

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users)
                .extracting(UserGetResponseDTO::email)
                .containsExactlyInAnyOrder("organizer1@example.com", "organizer2@example.com");
    }

}
