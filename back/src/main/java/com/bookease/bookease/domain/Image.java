package com.bookease.bookease.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private byte[] imageData;

    @Enumerated(EnumType.STRING)
    private ImageType type;  // Enum to specify context (EVENT or ORGANIZER)

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;  // Event can have multiple images.

    @OneToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;  // Organizer can have only one image.

    public enum ImageType {
        EVENT,
        ORGANIZER
    }
}

