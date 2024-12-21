package com.bookease.bookease.dtos.category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        String description

) {
}
