package com.bookease.bookease.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Role role;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDateTime dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //Cascade type faz as operações propagarem para os filhos (deletar user deleta
    //os tickets, orphan removal faz os tickets serem deletados se removidos do set
    private Set<Ticket> tickets;


    @ManyToMany
    @JoinTable(
            name = "user_event", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "event_id") // Foreign key for Event
    )
    private Set<Event> events;


}
