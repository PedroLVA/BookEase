package com.bookease.bookease.dtos.image;

import java.util.UUID;

public record ImageResponseDTO(
        UUID id,
        String type,
        String imageData
) {
}
