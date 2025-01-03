package com.bookease.bookease.services;

import com.bookease.bookease.domain.Event;
import com.bookease.bookease.domain.Image;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.image.ImageRequestDTO;
import com.bookease.bookease.dtos.image.ImageResponseDTO;
import com.bookease.bookease.dtos.mappers.ImageMapper;
import com.bookease.bookease.repositories.EventRepository;
import com.bookease.bookease.repositories.ImageRepository;
import com.bookease.bookease.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public ImageResponseDTO post(MultipartFile file, String entityType, UUID entityId) {
        try {
            var image = new Image();

            // Set common fields
            byte[] imageData = file.getBytes();
            image.setImageData(imageData);
            image.setInsertedOn(LocalDateTime.now());

            // Determine entity type and set relationship
            if ("EVENT".equalsIgnoreCase(entityType)) {
                Event event = eventRepository.findById(entityId)
                        .orElseThrow(() -> new RuntimeException("Event not found with ID: " + entityId));
                image.setEvent(event);
                image.setType(Image.ImageType.EVENT);
            } else if ("ORGANIZER".equalsIgnoreCase(entityType)) {
                User organizer = userRepository.findById(entityId)
                        .orElseThrow(() -> new RuntimeException("Organizer not found with ID: " + entityId));
                image.setOrganizer(organizer);
                image.setType(Image.ImageType.ORGANIZER);
            } else {
                throw new IllegalArgumentException("Invalid entity type: " + entityType);
            }

            // Save the image
            imageRepository.save(image);

            // Convert to DTO and return response
            return ImageMapper.toImageResponseDTO(image);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
    }


    public ImageResponseDTO getById(@NotNull UUID id) {
        var image = imageRepository.findById(id).orElseThrow( () -> new RuntimeException("Image not found"));
        return ImageMapper.toImageResponseDTO(image);
    }

    public List<Image> processImages(List<ImageRequestDTO> imageRequests, Event event) {
        try {
            return imageRequests.stream()
                    .map(imageRequest -> {
                        Image newImage = new Image();
                        newImage.setImageData(imageRequest.toByteArray());
                        newImage.setEvent(event);
                        return imageRepository.save(newImage);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error processing images: " + e.getMessage());
        }
    }

}
