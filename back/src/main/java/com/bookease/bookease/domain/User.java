package com.bookease.bookease.domain;

import com.bookease.bookease.dtos.user.UserRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Role role;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDateTime dateOfBirth;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiryTime;



    //custom constructor for registerDTo
    public User(UserRegisterRequestDTO data){
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.phoneNumber = data.phoneNumber();
        this.dateOfBirth = data.dateOfBirth();
        this.role = data.role() != null ? data.role() : Role.USER;
    }

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


    //User details methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role == Role.ORGANIZER) return List.of(new SimpleGrantedAuthority("ROLE_ORGANIZER"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        //return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
                //UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
                //UserDetails.super.isEnabled();
    }
}
