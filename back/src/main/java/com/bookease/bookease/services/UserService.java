package com.bookease.bookease.services;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetAllResponseDTO;
import com.bookease.bookease.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserGetAllResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserGetAllResponseDTO(
                        user.getId(),
                        user.getRole(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    public User registerNewUser(User user){
        return this.userRepository.save(user);
    }

}
