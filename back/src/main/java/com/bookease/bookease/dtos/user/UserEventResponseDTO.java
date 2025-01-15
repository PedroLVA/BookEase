package com.bookease.bookease.dtos.user;
import java.util.UUID;

public record UserEventResponseDTO(String id,
                                   String name,
                                   String email) {
}
