package com.bookease.bookease.services;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User registerNewUser(User user){
        return this.userRepository.save(user);
    }

}
