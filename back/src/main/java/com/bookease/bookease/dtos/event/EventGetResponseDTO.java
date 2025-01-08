package com.bookease.bookease.dtos.event;

import com.bookease.bookease.dtos.category.CategoryResponseDTO;
import com.bookease.bookease.dtos.image.ImageEventResponseDTO;
import com.bookease.bookease.dtos.image.ImageResponseDTO;
import com.bookease.bookease.dtos.user.UserEventResponseDTO;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventGetResponseDTO(
        UUID id,
        String name,
        String description,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        boolean isActive,
        LocalDateTime publishingDate,
        String address,
        String city,
        String state,
        String homeNumber,
        int capacity,
        UUID organizerId,
        Set<UserEventResponseDTO> attendees,
        Set<CategoryResponseDTO> categories,
        Set<UUID> imageIds

) {
}
