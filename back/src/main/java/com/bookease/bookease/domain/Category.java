package com.bookease.bookease.domain;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "events")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY) // Matches the relationship in Event
    private Set<Event> events;

    public Category(CategoryRequestDTO data){
        name = data.name();
        description = data.description();

    }
    public void addEvent(Event event) {
        if (events == null) {
            events = new HashSet<>();
        }
        events.add(event);
    }

}
