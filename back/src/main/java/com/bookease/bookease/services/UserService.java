package com.bookease.bookease.services;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        User user  = getUserByEmail(loggedInUserEmail);
        return user;

    }
    public User getUserByEmail(String email){
        return (User) userRepository.findByEmail(email);

    }

    public List<UserGetResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserGetResponseDTO(
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

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

}
