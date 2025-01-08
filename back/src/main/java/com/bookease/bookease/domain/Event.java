package com.bookease.bookease.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "events")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "categories")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private LocalDateTime startingDate; // Start date and time
    private LocalDateTime endingDate;

    private boolean isActive;

    private LocalDateTime publishingDate;

    // Maximum capacity of the event
    private int capacity;

    private int bookedSeats;

    //Endereço

    private String address;

    private String city;

    private String state;

    private String homeNumber;


    //here
    @ManyToMany
    @JoinTable(
            name = "event_user", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "event_id") // Foreign key for Event
    )
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

    public void addCategory(Category category) {
        if (categories == null) {
            categories = new HashSet<>();
        }
        categories.add(category);
    }

}
