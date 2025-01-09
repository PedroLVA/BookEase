package com.bookease.bookease.dtos.user;
import com.bookease.bookease.domain.Role;
import java.time.LocalDateTime;

public record RegisterDTO(String name,
                          String email,
                          String password,
                          String phoneNumber,
                          LocalDateTime dateOfBirth,
                          Role role) {
}
