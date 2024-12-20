package com.bookease.bookease.services;

import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetAllResponseDTO;
import com.bookease.bookease.repositories.OrganizerRepository;
import com.bookease.bookease.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    public List<UserGetAllResponseDTO> getAllUsers(){
        List<Organizer> users = organizerRepository.findAll();
        return users.stream()
                .map(organizer -> new UserGetAllResponseDTO(
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
