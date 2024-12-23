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
    private ImageType type;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    public enum ImageType {
        EVENT,
        ORGANIZER
    }
}

