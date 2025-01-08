package com.bookease.bookease.dtos.mappers;

import com.bookease.bookease.domain.Image;
import com.bookease.bookease.dtos.image.ImageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageMapper {
    public static ImageResponseDTO toImageResponseDTO(Image image) {
        return new ImageResponseDTO(
                image.getId(),
                image.getInsertedOn(),
                image.getType()
        );
    }
}
