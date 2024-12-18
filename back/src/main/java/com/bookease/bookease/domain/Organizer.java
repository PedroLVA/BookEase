package com.bookease.bookease.domain;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.Set;

public class Organizer extends User {

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;

    @OneToOne(mappedBy = "organizer")
    private Image image;

}
