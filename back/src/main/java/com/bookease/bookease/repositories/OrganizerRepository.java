package com.bookease.bookease.repositories;

import com.bookease.bookease.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

    UserDetails findByEmail(String email);
}
