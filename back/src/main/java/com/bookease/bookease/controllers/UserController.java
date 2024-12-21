package com.bookease.bookease.controllers;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all")
    public List<UserGetResponseDTO> getAllUsers() {
        return  this.userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
        User newUser = new User(userRegisterRequestDTO);
        userService.registerNewUser(newUser);

        return ResponseEntity.ok(newUser);
    }


}
