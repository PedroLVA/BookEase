package com.bookease.bookease.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "events")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private LocalDateTime date;

    private boolean isActive;

    private LocalDateTime publishingDate;

    //Endereço

    private String address;

    private String city;

    private String state;

    private String homeNumber;


    @ManyToMany(mappedBy = "events")
    private Set<User> attendees;

    @OneToMany(mappedBy = "event")
    private Set<Ticket> tickets;

    @ManyToMany
    @JoinTable(
            name = "event_category",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category>categories;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

}
