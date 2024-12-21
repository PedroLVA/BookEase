package com.bookease.bookease.dtos.user;

import java.util.UUID;

public record UserEventResponseDTO(UUID id,
                                   String name,
                                   String email) {
}
