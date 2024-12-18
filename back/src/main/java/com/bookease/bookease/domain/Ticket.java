package com.bookease.bookease.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "tickets")
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double ticketPrice;

    // Optional, for seated events
    private String seatNumber;

    private String ticketType;
    // Optional, for seated events

    private LocalDateTime validUntil;


    public enum PaymentStatus {
        PAID,
        PENDING,
        FAILED
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;



}
