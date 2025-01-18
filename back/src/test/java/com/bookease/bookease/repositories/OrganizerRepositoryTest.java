package com.bookease.bookease.repositories;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrganizerRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrganizerRepository organizerRepository;


    private Organizer organizer;

    @BeforeEach
    void setup(){
        organizer = new Organizer();
        organizer.setName("user");
        organizer.setEmail("user.ig@example.com");
        organizer.setPassword("Rockasa");
        organizer.setPhoneNumber("+1234567890");
        organizer.setDateOfBirth(LocalDateTime.now());
        organizer.setRole(Role.ORGANIZER);

        entityManager.persist(organizer);
        entityManager.flush();

    }

    @Test
    @DisplayName("Should return the correct organizer by email")
    void findByEmail() {
        Optional<Organizer> foundOrganizer = organizerRepository.findByEmail("user.ig@example.com");

        assertThat(foundOrganizer)
                .isPresent() // verifies Optional contains a value
                .hasValueSatisfying(org -> {
                    assertThat(org.getName()).isEqualTo("user");
                    assertThat(org.getEmail()).isEqualTo("user.ig@example.com");
                    assertThat(org.getRole()).isEqualTo(Role.ORGANIZER);
                });


    }

    @Test
    @DisplayName("Should return empty optional when email doesn't exist")
    void findByEmailNotFound() {
        Optional<Organizer> foundOrganizer = organizerRepository.findByEmail("nonexistent@example.com");

        assertThat(foundOrganizer).isEmpty();
    }
}