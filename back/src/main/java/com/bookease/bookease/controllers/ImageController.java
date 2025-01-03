package com.bookease.bookease.controllers;

import com.bookease.bookease.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/image")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

}
