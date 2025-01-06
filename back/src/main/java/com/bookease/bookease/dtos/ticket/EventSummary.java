package com.bookease.bookease.dtos.ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventSummary(
        UUID id,
        String name,
        String description,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        String address,
        String city,
        String state,
        String homeNumber
) {}
