package com.bookease.bookease.dtos.user;

import com.bookease.bookease.domain.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserGetResponseDTO(UUID id,
                                 Role role,
                                 String name,
                                 String email,
                                 String phoneNumber,
                                 LocalDateTime dateOfBirth) {
}

