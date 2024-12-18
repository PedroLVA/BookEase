package com.bookease.bookease.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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

    private String paymentStatus;
}
