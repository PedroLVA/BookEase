package com.bookease.bookease.domain;
import com.bookease.bookease.dtos.user.RegisterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ORGANIZER")
public class Organizer extends User {

    public Organizer(RegisterDTO data, String encryptedPassword) {
        super(data, encryptedPassword);
    }

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;

    @OneToOne(mappedBy = "organizer")
    private Image image;

}
