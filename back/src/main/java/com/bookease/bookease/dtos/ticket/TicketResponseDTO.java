package com.bookease.bookease.dtos.ticket;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponseDTO(
        String id,
        LocalDateTime bookingDate,
        LocalDateTime validUntil,
        String paymentStatus,
        Double ticketPrice,
        String seatNumber,
        String ticketType,
        EventSummary event,
        UserSummary user
) {
    public record EventSummary(
            String id,
            String name,
            String description,
            LocalDateTime startingDate,
            LocalDateTime endingDate,
            String address,
            String city,
            String state,
            String homeNumber
    ) {}

    public record UserSummary(
            String id,
            String name,
            String email
    ) {}
}
