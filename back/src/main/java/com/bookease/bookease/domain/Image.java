package com.bookease.bookease.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "images")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;  // Nullable if it's an organizer's image

    @OneToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;  // Nullable if it's an event's image

    @Enumerated(EnumType.STRING)
    private ImageType type;  // Enum to specify if it's an EVENT or ORGANIZER image

    public enum ImageType {
        EVENT,
        ORGANIZER
    }


}
