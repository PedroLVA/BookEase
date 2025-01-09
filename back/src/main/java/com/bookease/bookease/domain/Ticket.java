package com.bookease.bookease.domain;
import com.bookease.bookease.dtos.ticket.TicketRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double ticketPrice;

    private String seatNumber;

    private String ticketType;


    private LocalDateTime validUntil;


    public enum PaymentStatus {
        PAID,
        PENDING,
        FAILED
    }

    public Ticket(TicketRequestDTO data){
        this.bookingDate = data.bookingDate();
        this.paymentStatus = PaymentStatus.PENDING;
        this.ticketPrice = data.ticketPrice();
        this.seatNumber = data.seatNumber();
        this.ticketType = data.ticketType();
        this.validUntil = event.getEndingDate();

    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;


}
