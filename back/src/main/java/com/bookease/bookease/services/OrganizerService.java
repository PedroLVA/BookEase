package com.bookease.bookease.services;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.OrganizerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    public Organizer getOrganizerByEmail(String email){
        return (Organizer) organizerRepository.findByEmail(email);

    }

    public List<UserGetResponseDTO> getAllUsers(){
        List<Organizer> users = organizerRepository.findAll();
        return users.stream()
                .map(organizer -> new UserGetResponseDTO(
                        organizer.getId(),
                        organizer.getRole(),
                        organizer.getName(),
                        organizer.getEmail(),
                        organizer.getPhoneNumber(),
                        organizer.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    public User registerNewUser(Organizer organizer){
        return this.organizerRepository.save(organizer);
    }
}
