package com.bookease.bookease.controllers;
import com.bookease.bookease.dtos.image.ImageResponseDTO;
import com.bookease.bookease.services.ImageService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponseDTO> post(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entityType") String entityType,
            @RequestParam("entityId") UUID entityId) {
        try {
            return ResponseEntity.ok(imageService.post(file, entityType, entityId));
        } catch (Exception e) {
            throw new RuntimeException("Error while saving image: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponseDTO> getById(@PathVariable @NotNull UUID id) {
        try {
            return ResponseEntity.ok(imageService.getById(id));
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Image not found for ID: " + id, e);
        }
    }

    @GetMapping()
    public ResponseEntity<Set<ImageResponseDTO>> getAllImages() {
        try {
            return imageService.getAllImages();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching the images: ", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable @NotNull UUID id){
        try{
            imageService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Image not found for ID: " + id, e);
        }
    }

}