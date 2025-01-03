package com.bookease.bookease.dtos.image;

import com.bookease.bookease.domain.Image;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageResponseDTO(
        UUID id,
        String imageData64,
        LocalDateTime insertedOn,
        Image.ImageType type
) {
}
