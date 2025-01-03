package com.bookease.bookease.dtos.mappers;

import com.bookease.bookease.domain.Image;
import com.bookease.bookease.dtos.image.ImageResponseDTO;

import java.util.Base64;

public class ImageMapper {
    public static ImageResponseDTO toImageResponseDTO(Image image) {
        return new ImageResponseDTO(
                image.getId(),
                Base64.getEncoder().encodeToString(image.getImageData()),
                image.getInsertedOn(),
                image.getType()
        );
    }
}
