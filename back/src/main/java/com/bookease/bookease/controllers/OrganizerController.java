package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.LombokTest;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetAllResponseDTO;
import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import com.bookease.bookease.services.OrganizerService;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/organizer")
@AllArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping("/get-all")
    public List<UserGetAllResponseDTO> getAllUsers() {
        return  this.organizerService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
        Organizer newOrganizer = new Organizer(userRegisterRequestDTO);
        organizerService.registerNewUser(newOrganizer);

        return ResponseEntity.ok(newOrganizer);
    }

    @GetMapping("/user-methods")
    public ResponseEntity<String> getUserMethods() {
        LombokTest lombok = new LombokTest("Funcionou", 12);
        return ResponseEntity.ok(lombok.getName());
    }
}
