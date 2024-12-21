package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.dtos.event.EventRequestDTO;
import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final UserService userService;

}
