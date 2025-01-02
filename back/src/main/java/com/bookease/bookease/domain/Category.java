package com.bookease.bookease.domain;
import com.bookease.bookease.dtos.category.CategoryRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private String name;


    @ManyToMany(mappedBy = "categories") // Matches the relationship in Event
    private Set<Event> events;

    public Category(CategoryRequestDTO data){
        description = data.description();
        name = data.name();
    }

}
