package com.bookease.bookease.dtos.ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketRequestDTO(LocalDateTime bookingDate,
                               Double ticketPrice,
                               String seatNumber,
                               String ticketType,
                               LocalDateTime validUntil,
                               UUID eventId
                               ){ // Can be "PAID", "PENDING", or "FAILED") {
}
