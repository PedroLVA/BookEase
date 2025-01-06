package com.bookease.bookease.dtos.ticket;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponseDTO(UUID id,
                                LocalDateTime bookingDate,
                                LocalDateTime validUntil,
                                String paymentStatus,
                                Double ticketPrice,
                                String seatNumber,
                                String ticketType,
                                EventSummary event,
                                UserSummary user) {
}

