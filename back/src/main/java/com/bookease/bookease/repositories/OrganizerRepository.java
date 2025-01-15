package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<Organizer, String> {

    Optional<Organizer> findByEmail(String email);;
}
