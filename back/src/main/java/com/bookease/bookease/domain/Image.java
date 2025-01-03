package com.bookease.bookease.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private byte[] imageData;

    private @NotNull LocalDateTime insertedOn = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    public Image(byte[] imageData64) {
        this.imageData = imageData64;
    }

    public enum ImageType {
        EVENT,
        ORGANIZER
    }
}

