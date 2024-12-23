package com.bookease.bookease.controllers;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.services.UserService;
import lombok.AllArgsConstructor;
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

}
