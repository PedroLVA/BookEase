package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.AuthenticationDTO;
import com.bookease.bookease.dtos.user.LoginResponseDTO;
import com.bookease.bookease.dtos.user.RegisterDTO;
import com.bookease.bookease.infra.security.TokenService;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.UserRepository;
import com.bookease.bookease.services.OrganizerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final OrganizerRepository organizerRepository;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        var UsernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(UsernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Validated RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        if (data.role() == Role.USER) {
            User newUser = new User(data, encryptedPassword);
            this.userRepository.save(newUser);
        } else if (data.role() == Role.ORGANIZER) {
            Organizer newOrganizer = new Organizer(data, encryptedPassword); // Assuming Organizer has a similar constructor
            this.organizerRepository.save(newOrganizer);
        }

        return ResponseEntity.ok().build();
    }

}
