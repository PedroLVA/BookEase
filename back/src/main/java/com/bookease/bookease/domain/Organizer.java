package com.bookease.bookease.domain;


import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ORGANIZER")
public class Organizer extends User {



    public Organizer(UserRegisterRequestDTO data) {
        super(data);  // Call the User constructor with DTO data
    }

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;

    @OneToOne(mappedBy = "organizer")
    private Image image;

}
