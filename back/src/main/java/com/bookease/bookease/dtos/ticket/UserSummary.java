package com.bookease.bookease.dtos.ticket;

import java.util.UUID;

public record UserSummary(
        UUID id,
        String name,
        String email
) {}