package com.bookease.bookease.controllers;
import com.bookease.bookease.domain.LombokTest;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetAllResponseDTO;
import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all")
    public List<UserGetAllResponseDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(user -> new UserGetAllResponseDTO(
                        user.getId(),
                        user.getRole(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getDateOfBirth()))
                .collect(Collectors.toList());

    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
        User newUser = new User(userRegisterRequestDTO);
        userService.registerNewUser(newUser);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/user-methods")
    public ResponseEntity<String> getUserMethods() {
        LombokTest lombok = new LombokTest("Funcionou", 12);
        return ResponseEntity.ok(lombok.getName());
    }
}
