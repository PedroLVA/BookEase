package com.bookease.bookease.controllers;

import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.AuthenticationDTO;
import com.bookease.bookease.dtos.user.RegisterDTO;
import com.bookease.bookease.repositories.UserRepository;
import com.bookease.bookease.services.UserService;
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
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        var UsernamePassword = new UsernamePasswordAuthenticationToken(data .email(), data.password());
        var auth = this.authenticationManager.authenticate(UsernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data, encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
