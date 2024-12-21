package com.bookease.bookease.dtos.event;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;

import java.time.LocalDateTime;

public record EventRequestDTO(
        String name,
        String description,
        LocalDateTime date,
        String address,
        String city,
        String state,
        String homeNumber
) {
}
