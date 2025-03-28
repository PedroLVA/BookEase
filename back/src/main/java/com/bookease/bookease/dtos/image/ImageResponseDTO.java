package com.bookease.bookease.dtos.image;
import com.bookease.bookease.domain.Image;
import java.time.LocalDateTime;
import java.util.UUID;

public record ImageResponseDTO(
        String id,
        LocalDateTime insertedOn,
        Image.ImageType type
) {
}
