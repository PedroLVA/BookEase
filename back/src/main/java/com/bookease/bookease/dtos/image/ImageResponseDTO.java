package com.bookease.bookease.dtos.image;

import com.bookease.bookease.domain.Image;

import java.util.UUID;

public record ImageResponseDTO(
        UUID id,
        Image.ImageType type

) {
}
