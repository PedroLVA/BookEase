package com.bookease.bookease.services;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.OrganizerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrganizerServiceTest {

    @Mock
    private OrganizerRepository organizerRepository;

    @InjectMocks
    private OrganizerService organizerService;

    private Organizer mockOrganizer;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        Organizer organizer = new Organizer();
        //config inicial
        organizer.setId("53736b4c-c5cf-4d17-bb5b-e1fa70a3010c");
        organizer.setName("organizer");
        organizer.setEmail("organizer.ig@example.com");
        organizer.setPassword("Rockasa");
        organizer.setPhoneNumber("+1234567890");
        organizer.setDateOfBirth(LocalDateTime.now());
        organizer.setRole(Role.ORGANIZER);

        this.mockOrganizer = organizer;


    }

    @Test
    @DisplayName("Should retrieve the correct organizer by email")
    void getOrganizerByEmail() {
        Mockito.when(organizerRepository.findByEmail(mockOrganizer.getEmail())).thenReturn(Optional.of(mockOrganizer));

        Optional<Organizer> foundOrganizer = organizerService.getOrganizerByEmail(mockOrganizer.getEmail());

        assertThat(foundOrganizer)
                .isPresent() // verifies Optional contains a value
                .hasValueSatisfying(org -> {
                    assertThat(org.getName()).isEqualTo("organizer");
                    assertThat(org.getEmail()).isEqualTo("organizer.ig@example.com");
                    assertThat(org.getRole()).isEqualTo(Role.ORGANIZER);
                });

    }

    @Test
    @DisplayName("Should retrieve all organizers")
    void getAllOrganizers() {

        Organizer mockOrganizer2 = new Organizer();
        //config inicial
        mockOrganizer2.setId("53736b5c-c5cf-4d17-bb5b-e1fa70a3010c");
        mockOrganizer2.setName("mockOrganizer2");
        mockOrganizer2.setEmail("mockOrganizer2.ig@example.com");
        mockOrganizer2.setPassword("Rockasa");
        mockOrganizer2.setPhoneNumber("+1234567890");
        mockOrganizer2.setDateOfBirth(LocalDateTime.now());
        mockOrganizer2.setRole(Role.ORGANIZER);

        List<Organizer> listOfOrganizer = List.of(mockOrganizer, mockOrganizer2);

        Mockito.when(organizerRepository.findAll()).thenReturn(listOfOrganizer);

        List<UserGetResponseDTO> result = organizerService.getAllOrganizers();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(mockOrganizer.getId(), result.get(0).id());

        assertEquals(mockOrganizer2.getId(), result.get(1).id());

    }

    @Test
    @DisplayName("Should register new organizer successfully")
    void registerNewOrganizer() {
        Mockito.when(organizerRepository.save(mockOrganizer)).thenReturn(mockOrganizer);

        User result = organizerService.registerNewOrganizer(mockOrganizer);

        assertNotNull(result);
        assertEquals(result.getId(), mockOrganizer.getId());

        Mockito.verify(organizerRepository, Mockito.times(1)).save(mockOrganizer);

    }
}